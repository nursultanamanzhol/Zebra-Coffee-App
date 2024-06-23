@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.hiltAndroid) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.secretsGradlePlugin) apply false
    alias(libs.plugins.googleServices) apply false
}
