plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    id("app.cash.sqldelight") version "2.0.1"

}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
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
            linkerOpts.add("-lsqlite3")
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
            api(libs.kotlinx.coroutines)

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
            implementation(libs.kamel.image)

            //SQLDelight
            with(libs.sqldelight) {
                implementation(coroutine.ext)
                implementation(primitive.adapters)
            }
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
            implementation(libs.sqldelight.android.driver)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqldelight.native.driver)
            implementation(libs.stately.common)
        }
        jsMain.dependencies {
            implementation(libs.ktor.client.js)
            implementation(libs.web.worker.driver)
            implementation(devNpm("copy-webpack-plugin", "9.1.0"))
            implementation(npm("dateformat", "3.0.3"))
            implementation(npm("@cashapp/sqldelight-sqljs-worker", "2.0.0"))
            implementation(npm("sql.js", "1.8.0"))
            implementation("com.bumble.appyx:appyx-navigation-js:2.0.0-alpha09")
        }
    }

    targets.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().forEach{
        it.binaries.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.Framework>()
            .forEach { lib ->
                lib.isStatic = false
                lib.linkerOpts.add("-lsqlite3")
            }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.erendev.gemini"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

sqldelight {
    databases {
        create("AppDb") {
            packageName.set("com.erendev.gemini")
            generateAsync.set(true)
        }
    }
    linkSqlite.set(true)
}

compose.experimental {
    web.application {}
}