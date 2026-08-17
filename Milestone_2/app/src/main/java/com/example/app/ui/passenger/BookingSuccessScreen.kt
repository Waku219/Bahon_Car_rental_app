package com.example.app.ui.passenger

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.data.SampleData
import com.example.app.ui.components.CholoCard
import com.example.app.ui.components.CholoPrimaryButton
import com.example.app.ui.components.Glyph
import com.example.app.ui.components.InfoRow
import com.example.app.ui.theme.*
import com.example.app.util.toTaka

/** Wireframe 11 — booking confirmed. */
@Composable
fun BookingSuccessScreen(
    vehicleId: String?,
    onGoHome: () -> Unit
) {
    val vehicle = SampleData.vehicleById(vehicleId)
    val total = vehicle.pricePerDay * 2 + 500

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CholoWhite)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(72.dp))

        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(CholoGreenLight),
            contentAlignment = Alignment.Center
        ) {
            Glyph("✓", tint = CholoGreen, size = 46)
        }

        Spacer(Modifier.height(24.dp))

        Text(
            "বুকিং সফল হয়েছে!",
            style = MaterialTheme.typography.headlineMedium,
            color = Ink,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(4.dp))

        Text(
            "Your booking has been confirmed",
            style = MaterialTheme.typography.bodyMedium,
            color = InkMuted
        )

        Spacer(Modifier.height(32.dp))

        CholoCard {
            Column(Modifier.padding(16.dp)) {
                Text(
                    "বুকিং সারসংক্ষেপ (Booking Summary)",
                    style = MaterialTheme.typography.titleLarge,
                    color = Ink
                )
                Spacer(Modifier.height(4.dp))
                InfoRow("গাড়ি (Car)", vehicle.model)
                InfoRow("তারিখ (Dates)", "১৫ - ১৭ জানুয়ারি")
                HorizontalDivider(color = Line)
                InfoRow("মোট পরিশোধ (Paid)", total.toTaka(), emphasize = true)
            }
        }

        Spacer(Modifier.weight(1f))

        CholoPrimaryButton(text = "হোমে যান (Go to Home)", onClick = onGoHome)

        Spacer(Modifier.height(16.dp))
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun BookingSuccessPreview() {
    AppTheme { BookingSuccessScreen(vehicleId = "v1", onGoHome = {}) }
}
