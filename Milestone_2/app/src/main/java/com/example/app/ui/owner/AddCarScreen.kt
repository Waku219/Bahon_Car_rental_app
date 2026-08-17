package com.example.app.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.data.CarType
import com.example.app.ui.components.CholoDropdownField
import com.example.app.ui.components.CholoPrimaryButton
import com.example.app.ui.components.CholoTextField
import com.example.app.ui.components.CholoTopBar
import com.example.app.ui.components.Glyph
import com.example.app.ui.theme.*

/** Wireframe 13 — the owner lists a new car. */
@Composable
fun AddCarScreen(
    onBack: () -> Unit,
    onSubmit: () -> Unit
) {
    var model by remember { mutableStateOf("") }
    var brandYear by remember { mutableStateOf("") }
    var carType by remember { mutableStateOf(CarType.SEDAN.label) }
    var pricePerDay by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = { CholoTopBar("নতুন গাড়ি যুক্ত করুন (Add New Car)", onBack) },
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
                    label = "গাড়ির নাম/মডেল (Car Name/Model)",
                    value = model,
                    onValueChange = { model = it },
                    placeholder = "যেমন: Toyota Corolla"
                )
                CholoTextField(
                    label = "প্রস্তুতকারক ও সাল (Brand & Year)",
                    value = brandYear,
                    onValueChange = { brandYear = it },
                    placeholder = "যেমন: Toyota, 2018"
                )
                CholoDropdownField(
                    label = "গাড়ির ধরন (Car Type)",
                    selected = carType,
                    options = CarType.entries.map { it.label },
                    onSelect = { carType = it }
                )
                CholoTextField(
                    label = "দৈনিক ভাড়া (Price per Day)",
                    value = pricePerDay,
                    onValueChange = { pricePerDay = it },
                    placeholder = "যেমন: ৩৫০০",
                    prefix = "৳",
                    keyboardType = KeyboardType.Number
                )
                CholoTextField(
                    label = "লোকেশন (Location)",
                    value = location,
                    onValueChange = { location = it },
                    placeholder = "যেমন: উত্তরা, ঢাকা"
                )
                CholoTextField(
                    label = "গাড়ির বিবরণ (Description)",
                    value = description,
                    onValueChange = { description = it },
                    placeholder = "গাড়ির কন্ডিশন ও অন্যান্য সুবিধা উল্লেখ করুন...",
                    singleLine = false,
                    minLines = 4
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                "গাড়ির ছবি (Photos)",
                style = MaterialTheme.typography.labelLarge,
                color = Ink
            )
            Spacer(Modifier.height(8.dp))
            PhotoUploadBox(onClick = { /* TODO: image picker in the Firebase milestone */ })

            Spacer(Modifier.height(28.dp))

            CholoPrimaryButton(text = "গাড়ি যুক্ত করুন (Submit)", onClick = onSubmit)

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun PhotoUploadBox(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceAlt)
            .border(1.dp, Line, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Glyph("+", tint = CholoGreen, size = 24)
        Spacer(Modifier.height(8.dp))
        Text(
            "ছবি আপলোড করুন (Upload Photos)",
            style = MaterialTheme.typography.labelMedium,
            color = InkMuted
        )
    }
}

@Preview(showBackground = true, heightDp = 1400)
@Composable
private fun AddCarPreview() {
    AppTheme { AddCarScreen(onBack = {}, onSubmit = {}) }
}
