package com.example.app.ui.passenger

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.data.SampleData
import com.example.app.data.Vehicle
import com.example.app.ui.components.CarPhotoPlaceholder
import com.example.app.ui.components.CholoCard
import com.example.app.ui.components.CholoTopBar
import com.example.app.ui.components.RatingRow
import com.example.app.ui.theme.*
import com.example.app.util.toTaka

/** Wireframe 8 — the list of matching cars. */
@Composable
fun SearchResultsScreen(
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onCarClick: (String) -> Unit
) {
    Scaffold(
        topBar = { CholoTopBar("সার্চ রেজাল্ট (Search Results)", onBack) },
        containerColor = CholoWhite
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .background(CholoWhite)
        ) {
            // Active filter summary
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceAlt)
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "ঢাকা • সেডান • ১৫-১৭ জানুয়ারি",
                    style = MaterialTheme.typography.labelMedium,
                    color = InkMuted
                )
                Text(
                    "এডিট (Edit)",
                    style = MaterialTheme.typography.labelMedium,
                    color = CholoGreen,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable(onClick = onEdit)
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(SampleData.searchResults) { vehicle ->
                    VehicleResultCard(vehicle) { onCarClick(vehicle.id) }
                }
            }
        }
    }
}

@Composable
private fun VehicleResultCard(vehicle: Vehicle, onClick: () -> Unit) {
    CholoCard(onClick = onClick) {
        Row(Modifier.padding(12.dp)) {
            CarPhotoPlaceholder(
                modifier = Modifier
                    .width(96.dp)
                    .height(76.dp)
            )
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    "${vehicle.model} (${vehicle.brand})",
                    style = MaterialTheme.typography.titleMedium,
                    color = Ink
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "ক্যাটাগরি: ${vehicle.carType.label}",
                    style = MaterialTheme.typography.labelMedium,
                    color = InkMuted
                )
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "দৈনিক ভাড়া: ${vehicle.pricePerDay.toTaka()}",
                        style = MaterialTheme.typography.labelLarge,
                        color = CholoGreenDark
                    )
                    RatingRow(vehicle.rating)
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun SearchResultsPreview() {
    AppTheme { SearchResultsScreen(onBack = {}, onEdit = {}, onCarClick = {}) }
}
