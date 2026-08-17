package com.example.app.data

import com.example.app.util.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * The ONLY place in the app that talks to Firebase Auth and the users
 * collection. Screens and ViewModels never touch Firebase directly — that way,
 * swapping Firebase for something else later means editing this one file.
 */
class AuthRepository {

    // `by lazy` matters here: it means constructing an AuthRepository does NOT
    // touch Firebase. Without it, @Preview would crash, because Firebase isn't
    // initialised inside Android Studio's preview renderer.
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val db by lazy { FirebaseFirestore.getInstance() }

    /** True if a user is already logged in from a previous session. */
    val isSignedIn: Boolean get() = auth.currentUser != null

    val currentUserId: String? get() = auth.currentUser?.uid

    /**
     * Creates the Auth account, then writes the profile document to Firestore.
     *
     * Two separate systems: Firebase Auth stores only email + hashed password,
     * and gives back a uid. Everything else (name, NID, phone, role) goes into
     * Firestore under users/{uid}. The uid is what ties them together.
     */
    suspend fun register(
        name: String,
        email: String,
        password: String,
        phone: String,
        nid: String,
        nationality: String,
        address: String,
        role: UserRole
    ) {
        val result = auth.createUserWithEmailAndPassword(email.trim(), password).await()
        val uid = result?.user?.uid ?: throw IllegalStateException("অ্যাকাউন্ট তৈরি হয়নি")

        val profile = mapOf(
            "name" to name.trim(),
            "email" to email.trim(),
            "phone" to phone.trim(),
            "nid" to nid.trim(),
            "nationality" to nationality.trim(),
            "address" to address.trim(),
            "role" to role.name,
            "rating" to 0.0,
            "reviewCount" to 0,
            "createdAt" to System.currentTimeMillis()
        )

        db.collection("users").document(uid).set(profile).await()
    }

    /**
     * Signs in, then reads back which role this account was registered with.
     * The caller compares it against the role the user tapped, so an owner
     * can't accidentally sign in through the passenger screen.
     */
    suspend fun signIn(email: String, password: String): UserRole {
        auth.signInWithEmailAndPassword(email.trim(), password).await()

        val uid = auth.currentUser?.uid ?: throw IllegalStateException("লগইন হয়নি")
        val doc = db.collection("users").document(uid).get().await()
        val roleName = doc?.getString("role") ?: UserRole.PASSENGER.name

        return runCatching { UserRole.valueOf(roleName) }
            .getOrDefault(UserRole.PASSENGER)
    }

    fun signOut() {
        auth.signOut()
    }
}
