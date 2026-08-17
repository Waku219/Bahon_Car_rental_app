package com.example.app.ui.owner

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
import com.example.app.util.toBanglaNumber
import com.example.app.util.toTaka

/** Wireframe 15 — one booking request in full, with the payout breakdown. */
@Composable
fun BookingRequestDetailScreen(
    bookingId: String?,
    onBack: () -> Unit,
    onDecision: () -> Unit
) {
    val booking = SampleData.bookingById(bookingId)

    Scaffold(
        topBar = { CholoTopBar("বুকিং বিবরণ (Booking Detail)", onBack) },
        bottomBar = {
            Surface(color = CholoWhite, shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CholoOutlinedButton(
                        text = "প্রত্যাখ্যান (Decline)",
                        onClick = onDecision,
                        modifier = Modifier.weight(1f)
                    )
                    CholoPrimaryButton(
                        text = "গ্রহণ করুন (Accept)",
                        onClick = onDecision,
                        modifier = Modifier.weight(1f)
                    )
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
            CholoCard {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Avatar(booking.passengerName, size = 48.dp)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            booking.passengerName,
                            style = MaterialTheme.typography.titleLarge,
                            color = Ink
                        )
                        Spacer(Modifier.height(2.dp))
                        RatingRow(
                            booking.passengerRating,
                            suffix = "(${booking.passengerReviewCount.toBanglaNumber()} টি রিভিউ)"
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            CholoCard {
                Column(Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                    InfoRow("গাড়ির নাম (Car Name)", booking.vehicleModel)
                    HorizontalDivider(color = Line)
                    InfoRow("বুকিং তারিখ (Dates)", booking.dateRange)
                    HorizontalDivider(color = Line)
                    InfoRow("পিকআপ লোকেশন (Location)", booking.pickupLocation)
                }
            }

            Spacer(Modifier.height(16.dp))

            CholoCard(highlighted = true) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "ভাড়ার হিসাব (Price Breakdown)",
                        style = MaterialTheme.typography.titleLarge,
                        color = Ink
                    )
                    Spacer(Modifier.height(4.dp))
                    InfoRow(
                        "দৈনিক ভাড়া (${(booking.rentalFee / booking.days).toTaka()} × " +
                            "${booking.days.toBanglaNumber()} দিন)",
                        booking.rentalFee.toTaka()
                    )
                    InfoRow(
                        "সার্ভিস চার্জ (Platform Fee)",
                        "- ${booking.serviceCharge.toTaka()}"
                    )
                    HorizontalDivider(color = Line)
                    InfoRow(
                        "সর্বমোট বিল (Total Payout)",
                        booking.ownerPayout.toTaka(),
                        emphasize = true
                    )
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun BookingRequestDetailPreview() {
    AppTheme { BookingRequestDetailScreen(bookingId = "r1", onBack = {}, onDecision = {}) }
}
