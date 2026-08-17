package com.example.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.data.UserRole
import com.example.app.ui.components.CholoPrimaryButton
import com.example.app.ui.components.CholoTextField
import com.example.app.ui.components.Glyph
import com.example.app.ui.theme.*

/** Wireframes 4 and 5 — passenger and owner sign in, again one screen for both. */
@Composable
fun SignInScreen(
    role: UserRole,
    onBack: () -> Unit,
    onSignIn: () -> Unit,
    onSignUpClick: () -> Unit
) {
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isOwner = role == UserRole.OWNER

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CholoWhite)
            .verticalScroll(rememberScrollState())
    ) {
        Row(Modifier.padding(start = 8.dp, top = 8.dp)) {
            IconButton(onClick = onBack) {
                Glyph("←", tint = Ink, size = 22)
            }
        }

        Spacer(Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(CholoGreenLight),
                contentAlignment = Alignment.Center
            ) {
                Text("চলো", style = MaterialTheme.typography.titleMedium, color = CholoGreen)
            }

            Spacer(Modifier.height(16.dp))

            Text("চলো", style = MaterialTheme.typography.headlineSmall, color = Ink)

            Text(
                text = if (isOwner) "গাড়ির মালিক হিসেবে সাইন ইন করুন"
                else "যাত্রী হিসেবে সাইন ইন করুন",
                style = MaterialTheme.typography.headlineMedium,
                color = Ink,
                textAlign = TextAlign.Center
            )

            Text(
                if (isOwner) "Sign in as Car Owner" else "Sign in as Passenger",
                style = MaterialTheme.typography.bodyMedium,
                color = InkMuted
            )
        }

        Spacer(Modifier.height(36.dp))

        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            CholoTextField(
                label = "মোবাইল নম্বর (Phone Number)",
                value = phone,
                onValueChange = { phone = it },
                placeholder = "01712XXXXXX",
                prefix = "+880",
                keyboardType = KeyboardType.Phone
            )
            CholoTextField(
                label = "পাসওয়ার্ড (Password)",
                value = password,
                onValueChange = { password = it },
                placeholder = "আপনার পাসওয়ার্ড",
                isPassword = true
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                "পাসওয়ার্ড ভুলে গেছেন? (Forgot password?)",
                style = MaterialTheme.typography.labelMedium,
                color = CholoGreen,
                modifier = Modifier.clickable { /* TODO: forgot-password flow */ }
            )
        }

        Spacer(Modifier.height(48.dp))

        Column(Modifier.padding(horizontal = 20.dp)) {
            CholoPrimaryButton(text = "সাইন ইন করুন (Sign In)", onClick = onSignIn)
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("অ্যাকাউন্ট নেই? ", style = MaterialTheme.typography.bodyMedium, color = InkMuted)
            Text(
                "নতুন তৈরি করুন (Sign up)",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = CholoGreen,
                modifier = Modifier.clickable(onClick = onSignUpClick)
            )
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun SignInPreview() {
    AppTheme {
        SignInScreen(role = UserRole.PASSENGER, onBack = {}, onSignIn = {}, onSignUpClick = {})
    }
}
