package com.example.app.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.AuthRepository
import com.example.app.data.UserRole
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.launch

/** Everything the auth screens need to draw themselves. */
data class AuthUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

/**
 * Sits between the screens and AuthRepository.
 *
 * Why a ViewModel and not just `remember` inside the composable: a ViewModel
 * survives screen rotation and keeps the coroutine alive. If the user rotates
 * the phone mid-registration, `remember` state would be wiped and the network
 * call orphaned; the ViewModel carries on.
 */
class AuthViewModel : ViewModel() {

    private val repo = AuthRepository()

    var uiState by mutableStateOf(AuthUiState())
        private set

    fun clearError() {
        uiState = uiState.copy(error = null)
    }

    fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        phone: String,
        nid: String,
        nationality: String,
        address: String,
        role: UserRole
    ) {
        // Validate before touching the network — cheaper and gives faster feedback.
        val problem = when {
            name.isBlank() -> "নাম লিখুন"
            email.isBlank() -> "ইমেইল লিখুন"
            !email.contains("@") || !email.contains(".") -> "সঠিক ইমেইল লিখুন"
            phone.isBlank() -> "মোবাইল নম্বর লিখুন"
            password.length < 6 -> "পাসওয়ার্ড কমপক্ষে ৬ অক্ষরের হতে হবে"
            password != confirmPassword -> "দুইটা পাসওয়ার্ড মিলছে না"
            else -> null
        }
        if (problem != null) {
            uiState = uiState.copy(error = problem)
            return
        }

        uiState = AuthUiState(loading = true)
        viewModelScope.launch {
            try {
                repo.register(name, email, password, phone, nid, nationality, address, role)
                uiState = AuthUiState(success = true)
            } catch (e: Exception) {
                uiState = AuthUiState(error = e.toBanglaMessage())
            }
        }
    }

    fun signIn(email: String, password: String, expectedRole: UserRole) {
        val problem = when {
            email.isBlank() -> "ইমেইল লিখুন"
            password.isBlank() -> "পাসওয়ার্ড লিখুন"
            else -> null
        }
        if (problem != null) {
            uiState = uiState.copy(error = problem)
            return
        }

        uiState = AuthUiState(loading = true)
        viewModelScope.launch {
            try {
                val actualRole = repo.signIn(email, password)
                if (actualRole != expectedRole) {
                    // Registered as owner but signing in on the passenger screen
                    // (or the other way round). Sign back out and explain.
                    repo.signOut()
                    val registeredAs =
                        if (actualRole == UserRole.OWNER) "গাড়ির মালিক" else "যাত্রী"
                    uiState = AuthUiState(
                        error = "এই অ্যাকাউন্টটি $registeredAs হিসেবে নিবন্ধিত। " +
                            "সঠিক অপশন বেছে সাইন ইন করুন।"
                    )
                } else {
                    uiState = AuthUiState(success = true)
                }
            } catch (e: Exception) {
                uiState = AuthUiState(error = e.toBanglaMessage())
            }
        }
    }
}

/** Firebase's English exceptions turned into messages a user can act on. */
private fun Exception.toBanglaMessage(): String = when (this) {
    is FirebaseAuthUserCollisionException ->
        "এই ইমেইল দিয়ে আগেই একটি অ্যাকাউন্ট আছে। সাইন ইন করুন।"
    is FirebaseAuthWeakPasswordException ->
        "পাসওয়ার্ড খুব দুর্বল — কমপক্ষে ৬ অক্ষর দিন।"
    is FirebaseAuthInvalidUserException ->
        "এই ইমেইলে কোনো অ্যাকাউন্ট নেই।"
    is FirebaseAuthInvalidCredentialsException ->
        "ইমেইল বা পাসওয়ার্ড ভুল।"
    is FirebaseNetworkException ->
        "ইন্টারনেট সংযোগ পাওয়া যাচ্ছে না।"
    else ->
        message ?: "কিছু একটা সমস্যা হয়েছে, আবার চেষ্টা করুন।"
}
