package com.example.app.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.app.data.SampleData
import com.example.app.data.Vehicle
import com.example.app.data.VehicleStatus
import com.example.app.ui.components.Avatar
import com.example.app.ui.components.CholoCard
import com.example.app.ui.components.Glyph
import com.example.app.ui.components.SectionLabel
import com.example.app.ui.components.StatusChip
import com.example.app.ui.theme.*
import com.example.app.util.toBanglaNumber
import com.example.app.util.toTaka

/** Wireframe 12 — the car owner's dashboard. */
@Composable
fun OwnerHomeScreen(
    onAddCar: () -> Unit,
    onBookingRequests: () -> Unit,
    onSignOut: () -> Unit = {}
) {
    val owner = SampleData.currentOwner

    Scaffold(
        bottomBar = {
            Surface(color = CholoWhite, shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Button(
                        onClick = onBookingRequests,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CholoGreen,
                            contentColor = CholoWhite
                        )
                    ) {
                        Text(
                            "বুকিং অনুরোধ দেখুন (Booking Requests)",
                            style = MaterialTheme.typography.labelLarge
                        )
                        Spacer(Modifier.width(8.dp))
                        Glyph("→", tint = CholoWhite, size = 18)
                    }
                }
            }
        },
        containerColor = CholoWhite
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
                            "চলো (Cholo) • পার্টনার",
                            style = MaterialTheme.typography.labelMedium,
                            color = InkMuted
                        )
                        Text(
                            "স্বাগতম, ${owner.name}",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Ink
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Avatar(owner.name)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "লগ আউট",
                            style = MaterialTheme.typography.labelSmall,
                            color = CholoRed,
                            modifier = Modifier.clickable(onClick = onSignOut)
                        )
                    }
                }
            }

            item {
                Spacer(Modifier.height(20.dp))
                OutlinedButton(
                    onClick = onAddCar,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(56.dp),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, CholoGreen),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CholoGreen)
                ) {
                    Glyph("+", tint = CholoGreen, size = 20)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "নতুন গাড়ি যুক্ত করুন (Add New Car)",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Spacer(Modifier.height(28.dp))
                SectionLabel(
                    "আমার গাড়িসমূহ (My Cars)",
                    Modifier.padding(horizontal = 20.dp)
                )
                Spacer(Modifier.height(12.dp))
            }

            items(SampleData.myCars) { vehicle ->
                MyCarCard(
                    vehicle = vehicle,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
private fun MyCarCard(vehicle: Vehicle, modifier: Modifier = Modifier) {
    val statusColor = when (vehicle.status) {
        VehicleStatus.ACTIVE -> StatusCompleted
        VehicleStatus.RENTED -> StatusRented
        VehicleStatus.INACTIVE -> InkMuted
    }

    CholoCard(modifier = modifier) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${vehicle.model} (${vehicle.plateNumber})",
                    style = MaterialTheme.typography.titleMedium,
                    color = Ink,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                StatusChip(vehicle.status.label, statusColor)
            }
            Spacer(Modifier.height(10.dp))
            Text(
                "আজকের আয়: ${vehicle.todayEarnings.toTaka()}",
                style = MaterialTheme.typography.bodyMedium,
                color = InkMuted
            )
            Text(
                "চলতি মাসে ট্রিপ: ${vehicle.tripsThisMonth.toBanglaNumber()} টি",
                style = MaterialTheme.typography.bodyMedium,
                color = InkMuted
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun OwnerHomePreview() {
    AppTheme { OwnerHomeScreen(onAddCar = {}, onBookingRequests = {}) }
}
