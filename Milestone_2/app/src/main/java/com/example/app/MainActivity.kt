package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.app.navigation.CholoNavigation
import com.example.app.ui.theme.AppTheme

/**
 * Single-Activity architecture: this is the app's only Activity.
 * Every screen is a @Composable, and CholoNavigation decides which one shows.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                CholoNavigation()
            }
        }
    }
}
