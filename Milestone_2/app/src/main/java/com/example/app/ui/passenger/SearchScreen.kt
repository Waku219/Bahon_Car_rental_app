package com.example.app.ui.passenger

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.data.CarType
import com.example.app.ui.components.CholoDropdownField
import com.example.app.ui.components.CholoPrimaryButton
import com.example.app.ui.components.CholoTextField
import com.example.app.ui.components.CholoTopBar
import com.example.app.ui.theme.AppTheme
import com.example.app.ui.theme.CholoWhite

/** Wireframe 7 — the search form. */
@Composable
fun SearchScreen(
    onBack: () -> Unit,
    onSearch: () -> Unit
) {
    var location by remember { mutableStateOf("ঢাকা, বাংলাদেশ") }
    var carType by remember { mutableStateOf(CarType.SEDAN.label) }
    var pickupDate by remember { mutableStateOf("") }
    var returnDate by remember { mutableStateOf("") }

    Scaffold(
        topBar = { CholoTopBar("গাড়ি খুঁজুন (Search Cars)", onBack) },
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
            Spacer(Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                CholoTextField(
                    label = "অবস্থান (Location)",
                    value = location,
                    onValueChange = { location = it },
                    placeholder = "ঢাকা, বাংলাদেশ"
                )
                CholoDropdownField(
                    label = "গাড়ির ধরন (Car Type)",
                    selected = carType,
                    options = CarType.entries.map { it.label },
                    onSelect = { carType = it }
                )
                // A real date picker comes later — for now these are plain fields
                // so the screen matches the wireframe and stays tappable.
                CholoTextField(
                    label = "পিকআপ তারিখ (Pickup Date)",
                    value = pickupDate,
                    onValueChange = { pickupDate = it },
                    placeholder = "নির্বাচন করুন (Select Date)"
                )
                CholoTextField(
                    label = "ফেরত তারিখ (Return Date)",
                    value = returnDate,
                    onValueChange = { returnDate = it },
                    placeholder = "নির্বাচন করুন (Select Date)"
                )
            }

            // NOTE: no Modifier.weight(1f) here — weight is illegal inside a
            // verticalScroll Column (the height is unbounded, so there's nothing
            // to take a fraction of). Fixed spacing instead.
            Spacer(Modifier.height(64.dp))

            CholoPrimaryButton(text = "খুঁজুন (Search)", onClick = onSearch)

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun SearchPreview() {
    AppTheme { SearchScreen(onBack = {}, onSearch = {}) }
}
