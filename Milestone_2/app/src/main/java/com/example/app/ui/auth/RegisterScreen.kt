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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.data.UserRole
import com.example.app.ui.components.CholoPrimaryButton
import com.example.app.ui.components.CholoTextField
import com.example.app.ui.components.Glyph
import com.example.app.ui.theme.*

/**
 * Wireframes 2 and 3 — passenger and owner registration.
 * They are byte-for-byte the same form, so this is ONE screen with a
 * `role` parameter rather than two near-identical files.
 */
@Composable
fun RegisterScreen(
    role: UserRole,
    onBack: () -> Unit,
    onRegister: () -> Unit,
    onSignInClick: () -> Unit
) {
    // `remember` keeps the value across recompositions; `mutableStateOf` makes
    // Compose redraw the field whenever it changes. This pair is the whole
    // state model in Compose.
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var nid by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("বাংলাদেশী") }
    var address by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(CholoGreenLight),
                contentAlignment = Alignment.Center
            ) {
                Text("চলো", style = MaterialTheme.typography.labelLarge, color = CholoGreen)
            }

            Spacer(Modifier.height(12.dp))

            Text("চলো", style = MaterialTheme.typography.headlineSmall, color = Ink)

            Text(
                if (isOwner) "গাড়ির মালিক রেজিস্ট্রেশন" else "যাত্রী রেজিস্ট্রেশন",
                style = MaterialTheme.typography.headlineMedium,
                color = Ink
            )

            Text(
                if (isOwner) "Sign up as Car Owner" else "Sign up as Passenger",
                style = MaterialTheme.typography.bodyMedium,
                color = InkMuted
            )
        }

        Spacer(Modifier.height(28.dp))

        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            CholoTextField(
                label = "পূর্ণ নাম (Full Name)",
                value = name,
                onValueChange = { name = it },
                placeholder = "আপনার সম্পূর্ণ নাম লিখুন"
            )
            CholoTextField(
                label = "মোবাইল নম্বর (Phone Number)",
                value = phone,
                onValueChange = { phone = it },
                placeholder = "01712XXXXXX",
                prefix = "+880",
                keyboardType = KeyboardType.Phone
            )
            CholoTextField(
                label = "জাতীয় পরিচয়পত্র নম্বর (NID Number)",
                value = nid,
                onValueChange = { nid = it },
                placeholder = "NID নম্বর লিখুন",
                keyboardType = KeyboardType.Number
            )
            CholoTextField(
                label = "জাতীয়তা (Nationality)",
                value = nationality,
                onValueChange = { nationality = it }
            )
            CholoTextField(
                label = "ঠিকানা (Address)",
                value = address,
                onValueChange = { address = it },
                placeholder = "আপনার বর্তমান ও স্থায়ী ঠিকানা লিখুন",
                singleLine = false,
                minLines = 3
            )
            CholoTextField(
                label = "ইমেইল (Email) — ঐচ্ছিক",
                value = email,
                onValueChange = { email = it },
                placeholder = "example@mail.com",
                keyboardType = KeyboardType.Email
            )
            CholoTextField(
                label = "পাসওয়ার্ড (Password)",
                value = password,
                onValueChange = { password = it },
                placeholder = "পাসওয়ার্ড তৈরি করুন",
                isPassword = true
            )
            CholoTextField(
                label = "পাসওয়ার্ড নিশ্চিত করুন (Confirm Password)",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "পাসওয়ার্ডটি পুনরায় লিখুন",
                isPassword = true
            )
        }

        Spacer(Modifier.height(32.dp))

        Column(Modifier.padding(horizontal = 20.dp)) {
            CholoPrimaryButton(
                text = "রেজিস্ট্রেশন সম্পন্ন করুন (Complete Registration)",
                onClick = onRegister
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("অ্যাকাউন্ট আছে? ", style = MaterialTheme.typography.bodyMedium, color = InkMuted)
            Text(
                "সাইন ইন করুন (Sign In)",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = CholoGreen,
                modifier = Modifier.clickable(onClick = onSignInClick)
            )
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Preview(showBackground = true, heightDp = 1600)
@Composable
private fun RegisterPreview() {
    AppTheme {
        RegisterScreen(
            role = UserRole.PASSENGER,
            onBack = {}, onRegister = {}, onSignInClick = {}
        )
    }
}
