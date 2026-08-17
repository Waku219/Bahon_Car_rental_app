// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    // Reads google-services.json and generates the Firebase config resources.
    // `apply false` here — it is actually applied in app/build.gradle.kts.
    alias(libs.plugins.google.services) apply false
}
