plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.20"
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
    }
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.15.1")
    }
}
