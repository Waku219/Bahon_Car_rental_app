package com.example.app.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.data.Booking
import com.example.app.data.SampleData
import com.example.app.ui.components.Avatar
import com.example.app.ui.components.CholoCard
import com.example.app.ui.components.CholoOutlinedButton
import com.example.app.ui.components.CholoPrimaryButton
import com.example.app.ui.components.CholoTopBar
import com.example.app.ui.components.RatingRow
import com.example.app.ui.theme.*
import com.example.app.util.toBanglaNumber
import com.example.app.util.toTaka

/** Wireframe 14 — incoming booking requests the owner must accept or decline. */
@Composable
fun BookingRequestsScreen(
    onBack: () -> Unit,
    onRequestClick: (String) -> Unit
) {
    Scaffold(
        topBar = { CholoTopBar("বুকিং অনুরোধ (Booking Requests)", onBack) },
        containerColor = CholoWhite
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(CholoWhite),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(SampleData.bookingRequests) { request ->
                RequestCard(
                    booking = request,
                    onClick = { onRequestClick(request.id) }
                )
            }
        }
    }
}

@Composable
private fun RequestCard(booking: Booking, onClick: () -> Unit) {
    CholoCard(onClick = onClick) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Avatar(booking.passengerName, size = 36.dp)
                    Spacer(Modifier.width(10.dp))
                    Text(
                        booking.passengerName,
                        style = MaterialTheme.typography.titleMedium,
                        color = Ink
                    )
                }
                RatingRow(booking.passengerRating)
            }

            Spacer(Modifier.height(14.dp))

            Text(
                "গাড়ি: ${booking.vehicleModel}",
                style = MaterialTheme.typography.bodyMedium,
                color = Ink
            )
            Text(
                "তারিখ: ${booking.dateRange} (${booking.days.toBanglaNumber()} দিন)",
                style = MaterialTheme.typography.bodyMedium,
                color = InkMuted
            )
            Text(
                "মোট প্রস্তাবিত ভাড়া: ${booking.rentalFee.toTaka()}",
                style = MaterialTheme.typography.bodyMedium,
                color = InkMuted
            )

            Spacer(Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CholoOutlinedButton(
                    text = "প্রত্যাখ্যান (Decline)",
                    onClick = { /* TODO: wire to repository */ },
                    modifier = Modifier.weight(1f)
                )
                CholoPrimaryButton(
                    text = "গ্রহণ করুন (Accept)",
                    onClick = { /* TODO: wire to repository */ },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun BookingRequestsPreview() {
    AppTheme { BookingRequestsScreen(onBack = {}, onRequestClick = {}) }
}
