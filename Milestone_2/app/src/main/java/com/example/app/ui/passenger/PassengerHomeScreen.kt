package com.example.app.ui.passenger

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.data.Booking
import com.example.app.data.BookingStatus
import com.example.app.data.CarType
import com.example.app.data.SampleData
import com.example.app.ui.components.Avatar
import com.example.app.ui.components.CholoCard
import com.example.app.ui.components.SearchGlyph
import com.example.app.ui.components.SectionLabel
import com.example.app.ui.components.StatusChip
import com.example.app.ui.components.VerifiedFooter
import com.example.app.ui.theme.*
import com.example.app.util.toTaka

/** Wireframe 6 — the passenger's landing screen after sign in. */
@Composable
fun PassengerHomeScreen(
    onSearchClick: () -> Unit,
    onCategoryClick: (CarType) -> Unit,
    onBookingClick: (String) -> Unit
) {
    val user = SampleData.currentPassenger

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CholoWhite),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "চলো (Cholo)",
                        style = MaterialTheme.typography.labelMedium,
                        color = InkMuted
                    )
                    Text(
                        "স্বাগতম, ${user.name}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Ink
                    )
                }
                Avatar(user.name)
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(SurfaceAlt)
                    .border(1.dp, Line, RoundedCornerShape(28.dp))
                    .clickable(onClick = onSearchClick)
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchGlyph()
                Spacer(Modifier.width(12.dp))
                Text(
                    "গাড়ি খুঁজুন (Search for cars)",
                    style = MaterialTheme.typography.bodyLarge,
                    color = InkMuted
                )
            }
        }

        item {
            SectionLabel(
                "ক্যাটাগরি (Categories)",
                Modifier.padding(horizontal = 20.dp)
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CarType.entries.forEach { type ->
                    CategoryTile(
                        label = type.label,
                        onClick = { onCategoryClick(type) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(Modifier.height(28.dp))
        }

        item {
            SectionLabel(
                "সাম্প্রতিক বুকিং (Recent Bookings)",
                Modifier.padding(horizontal = 20.dp)
            )
            Spacer(Modifier.height(12.dp))
        }

        items(SampleData.recentBookings) { booking ->
            RecentBookingCard(
                booking = booking,
                onClick = { onBookingClick("v1") },
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
            )
        }

        item {
            Spacer(Modifier.height(24.dp))
            VerifiedFooter()
        }
    }
}

@Composable
private fun CategoryTile(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(CholoWhite)
            .border(1.dp, Line, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(CholoGreenLight)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            label,
            style = MaterialTheme.typography.labelMedium,
            color = Ink
        )
    }
}

@Composable
private fun RecentBookingCard(
    booking: Booking,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val statusColor = when (booking.status) {
        BookingStatus.ONGOING -> StatusOngoing
        BookingStatus.COMPLETED -> StatusCompleted
        BookingStatus.PENDING -> StatusPending
        BookingStatus.DECLINED -> CholoRed
    }

    CholoCard(modifier = modifier, onClick = onClick) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    booking.vehicleModel,
                    style = MaterialTheme.typography.titleLarge,
                    color = Ink
                )
                StatusChip(booking.status.label, statusColor)
            }
            Spacer(Modifier.height(10.dp))
            Text(
                "তারিখ: ${booking.dateRange}",
                style = MaterialTheme.typography.bodyMedium,
                color = InkMuted
            )
            Text(
                "মোট ভাড়া: ${booking.total.toTaka()}",
                style = MaterialTheme.typography.bodyMedium,
                color = InkMuted
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun PassengerHomePreview() {
    AppTheme {
        PassengerHomeScreen(onSearchClick = {}, onCategoryClick = {}, onBookingClick = {})
    }
}
