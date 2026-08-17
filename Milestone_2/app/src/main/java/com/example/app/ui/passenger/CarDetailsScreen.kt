package com.example.app.ui.passenger

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.data.SampleData
import com.example.app.ui.components.*
import com.example.app.ui.theme.*
import com.example.app.util.toBanglaDigits
import com.example.app.util.toBanglaNumber
import com.example.app.util.toTaka

/** Wireframe 9 — full detail for one car. */
@Composable
fun CarDetailsScreen(
    vehicleId: String?,
    onBack: () -> Unit,
    onBookNow: () -> Unit
) {
    val vehicle = SampleData.vehicleById(vehicleId)

    Scaffold(
        topBar = { CholoTopBar("গাড়ির বিবরণ (Car Details)", onBack) },
        bottomBar = {
            Surface(color = CholoWhite, shadowElevation = 8.dp) {
                Box(Modifier.padding(20.dp)) {
                    CholoPrimaryButton(text = "বুক করুন (Book Now)", onClick = onBookNow)
                }
            }
        },
        containerColor = CholoWhite
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(CholoWhite)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            CarPhotoPlaceholder(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp),
                label = "Car Detail Large Image"
            )

            Spacer(Modifier.height(20.dp))

            Text(
                // year uses raw digits, not toBanglaNumber(), so it doesn't
                // come out as "২,০২২" with a thousands separator
                "${vehicle.model} ${vehicle.year.toString().toBanglaDigits()}",
                style = MaterialTheme.typography.headlineMedium,
                color = Ink
            )

            Spacer(Modifier.height(4.dp))

            Text(
                "দৈনিক ভাড়া: ${vehicle.pricePerDay.toTaka()} (Rental: ৳${vehicle.pricePerDay}/day)",
                style = MaterialTheme.typography.titleMedium,
                color = CholoGreenDark
            )

            Spacer(Modifier.height(24.dp))

            SectionLabel("বৈশিষ্ট্যসমূহ (Specifications)")

            Spacer(Modifier.height(4.dp))

            InfoRow("ট্রান্সমিশন (Transmission)", vehicle.transmission)
            HorizontalDivider(color = Line)
            InfoRow("আসনসংখ্যা (Seats)", "${vehicle.seats.toBanglaNumber()} সিট")
            HorizontalDivider(color = Line)
            InfoRow("জ্বালানি (Fuel Type)", vehicle.fuelType)
            HorizontalDivider(color = Line)
            InfoRow("অবস্থান (Location)", vehicle.location)

            Spacer(Modifier.height(20.dp))

            CholoCard {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Avatar(vehicle.ownerName)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            "মালিক: ${vehicle.ownerName}",
                            style = MaterialTheme.typography.titleMedium,
                            color = Ink
                        )
                        Spacer(Modifier.height(2.dp))
                        RatingRow(
                            vehicle.ownerRating,
                            suffix = "(${vehicle.ownerReviewCount.toBanglaNumber()}+ রিভিউ)"
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            SectionLabel("বিবরণ (Description)")
            Spacer(Modifier.height(8.dp))
            Text(
                vehicle.description,
                style = MaterialTheme.typography.bodyMedium,
                color = InkMuted
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun CarDetailsPreview() {
    AppTheme { CarDetailsScreen(vehicleId = "v1", onBack = {}, onBookNow = {}) }
}
