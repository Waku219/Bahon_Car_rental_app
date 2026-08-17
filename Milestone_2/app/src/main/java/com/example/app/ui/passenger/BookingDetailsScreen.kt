package com.example.app.ui.passenger

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.data.SampleData
import com.example.app.ui.components.*
import com.example.app.ui.theme.*
import com.example.app.util.toBanglaNumber
import com.example.app.util.toTaka

/** Wireframe 10 — confirm dates, location and see the price breakdown. */
@Composable
fun BookingDetailsScreen(
    vehicleId: String?,
    onBack: () -> Unit,
    onConfirm: () -> Unit
) {
    val vehicle = SampleData.vehicleById(vehicleId)

    var pickup by remember { mutableStateOf("১৫ জানুয়ারি, ১০:০০ পূর্বাহ্ন") }
    var returnAt by remember { mutableStateOf("১৭ জানুয়ারি, ১০:০০ পূর্বাহ্ন") }
    var pickupLocation by remember { mutableStateOf("ধানমন্ডি, ঢাকা") }

    // Hardcoded 2-day rental for now; once dates are real this becomes a calculation.
    val days = 2
    val rentalFee = vehicle.pricePerDay * days
    val serviceCharge = 500L
    val total = rentalFee + serviceCharge

    Scaffold(
        topBar = { CholoTopBar("বুকিং বিবরণ (Booking Details)", onBack) },
        bottomBar = {
            Surface(color = CholoWhite, shadowElevation = 8.dp) {
                Box(Modifier.padding(20.dp)) {
                    CholoPrimaryButton(
                        text = "বুকিং নিশ্চিত করুন (Confirm Booking)",
                        onClick = onConfirm
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
            Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                CholoTextField(
                    label = "পিকআপ তারিখ ও সময় (Pickup Date & Time)",
                    value = pickup,
                    onValueChange = { pickup = it }
                )
                CholoTextField(
                    label = "ফেরত তারিখ ও সময় (Return Date & Time)",
                    value = returnAt,
                    onValueChange = { returnAt = it }
                )
                CholoTextField(
                    label = "পিকআপ লোকেশন (Pickup Location)",
                    value = pickupLocation,
                    onValueChange = { pickupLocation = it }
                )
            }

            Spacer(Modifier.height(24.dp))

            CholoCard {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "ভাড়ার বিবরণ (Price Details)",
                        style = MaterialTheme.typography.titleLarge,
                        color = Ink
                    )
                    Spacer(Modifier.height(4.dp))
                    InfoRow(
                        "ভাড়া (Rental Fee) - ${days.toBanglaNumber()} দিন",
                        rentalFee.toTaka()
                    )
                    InfoRow("সার্ভিস চার্জ (Service Charge)", serviceCharge.toTaka())
                    HorizontalDivider(color = Line)
                    InfoRow("মোট (Total)", total.toTaka(), emphasize = true)
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun BookingDetailsPreview() {
    AppTheme { BookingDetailsScreen(vehicleId = "v1", onBack = {}, onConfirm = {}) }
}
