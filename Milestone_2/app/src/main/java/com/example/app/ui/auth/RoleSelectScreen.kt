package com.example.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.ui.components.CholoCard
import com.example.app.ui.components.Glyph
import com.example.app.ui.components.VerifiedFooter
import com.example.app.ui.theme.*

/** Wireframe 1 — the landing screen where the user picks owner or passenger. */
@Composable
fun RoleSelectScreen(
    onOwnerClick: () -> Unit,
    onPassengerClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CholoWhite)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))

        // Logo placeholder — swap for an Image() when you have the real asset
        Box(
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .background(CholoGreenLight),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "চলো",
                style = MaterialTheme.typography.titleLarge,
                color = CholoGreen
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            "চলো",
            style = MaterialTheme.typography.displaySmall,
            color = Ink
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "গাড়ি ভাড়ার সহজ সমাধান",
            style = MaterialTheme.typography.bodyMedium,
            color = InkMuted
        )

        Spacer(Modifier.height(32.dp))

        RoleCard(
            tag = "পার্টনার হোস্ট",
            title = "গাড়ি ভাড়া দিবেন?",
            description = "আপনার গাড়ি ভাড়া দিয়ে প্রতি মাসে বাড়তি আয় করুন। " +
                "আপনি নিজেই ঠিক করবেন কখন এবং কার কাছে ভাড়া দিবেন।",
            onClick = onOwnerClick
        )

        Spacer(Modifier.height(16.dp))

        RoleCard(
            tag = "ভাড়া খুঁজুন",
            title = "গাড়ি ভাড়া নিবেন?",
            description = "ভেরিফাইড মালিকদের কাছ থেকে সাশ্রয়ী দামে গাড়ি ভাড়া নিন। " +
                "কোনো লুকানো চার্জ নেই।",
            onClick = onPassengerClick
        )

        Spacer(Modifier.height(24.dp))
        VerifiedFooter()
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun RoleCard(
    tag: String,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    CholoCard(onClick = onClick) {
        Column(Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(CholoGreenLight)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        tag,
                        style = MaterialTheme.typography.labelMedium,
                        color = CholoGreenDark,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Glyph("→", tint = CholoGreen, size = 20)
            }

            Spacer(Modifier.height(20.dp))

            Text(
                title,
                style = MaterialTheme.typography.headlineSmall,
                color = Ink
            )

            Spacer(Modifier.height(8.dp))

            Text(
                description,
                style = MaterialTheme.typography.bodyMedium,
                color = InkMuted
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RoleSelectPreview() {
    AppTheme { RoleSelectScreen(onOwnerClick = {}, onPassengerClick = {}) }
}
