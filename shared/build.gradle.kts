plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("org.jetbrains.compose")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.animation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

            //Coroutines
            implementation(libs.kotlinx.coroutines)

            //Ktor
            with(libs.ktor.client) {
                implementation(core)
                implementation(content.negotiation)
                implementation(serialization)
                api(logging)
            }

            //Settings
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.serialization)

            //Kermit
            implementation(libs.kermit)

            //Koin
            implementation(libs.koin.core)

            implementation(libs.appyx.navigation)
            implementation(libs.appyx.interactions)
            api(libs.backstack)
            implementation(libs.spotlight)
            implementation(libs.voyager.bottomSheetNavigator)
            implementation(libs.compose.materialmotion)
            implementation(libs.kamel.image)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.security.crypto.ktx)
            api(libs.koin.android)
            api(libs.voyager.androidx)
            api(libs.appyx.navigation)
            api(libs.backstack.android)
            api(libs.spotlight.android)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.erendev.gemini"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
