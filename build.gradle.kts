// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) version "2.0.10" apply false
    kotlin("jvm") version "2.0.10"
    // alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}