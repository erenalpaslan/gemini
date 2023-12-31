import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.compose.internal.de.undercouch.gradle.tasks.download.Download

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    id("app.cash.sqldelight") version "2.0.1"
    kotlin("plugin.serialization")
    id("com.codingfeline.buildkonfig")
}

kotlin {
    js {
        moduleName = "gemini"
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
            implementation(libs.koin.core.coroutine)

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

            //Date
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
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
            implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqldelight.native.driver)
            implementation(libs.stately.common)
        }
        jsMain.dependencies {
            implementation(libs.ktor.client.js)
            implementation(libs.web.worker.driver)
            implementation("app.cash.sqldelight:web-worker-driver:2.0.0")
            implementation(devNpm("copy-webpack-plugin", "9.1.0"))
            implementation(npm("@cashapp/sqldelight-sqljs-worker", "2.0.0"))
            implementation(npm("sql.js", "1.8.0"))
        }
        jsMain {
            resources.srcDir(layout.buildDirectory.dir("sqlite"))
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

buildkonfig {
    packageName = "com.erendev.gemini"
    // objectName = "YourAwesomeConfig"
    // exposeObjectWithName = "YourAwesomePublicConfig"

    defaultConfigs {
        buildConfigField(STRING, "BASE_URL", "https://generativelanguage.googleapis.com/")
        buildConfigField(STRING, "API_KEY", gradleLocalProperties(rootDir).getProperty("API_KEY"))
    }
}

// See https://sqlite.org/download.html for the latest wasm build version
val sqlite = 3400000

val sqliteDownload = tasks.register("sqliteDownload", Download::class.java) {
    src("https://sqlite.org/2022/sqlite-wasm-$sqlite.zip")
    dest(layout.buildDirectory.dir("tmp"))
    onlyIfModified(true)
}

val sqliteUnzip = tasks.register("sqliteUnzip", Copy::class.java) {
    dependsOn(sqliteDownload)
    from(zipTree(layout.buildDirectory.dir("tmp/sqlite-wasm-$sqlite.zip"))) {
        include("sqlite-wasm-$sqlite/jswasm/**")
        exclude("**/*worker1*")

        eachFile {
            relativePath = RelativePath(true, *relativePath.segments.drop(2).toTypedArray())
        }
    }
    into(layout.buildDirectory.dir("sqlite"))
    includeEmptyDirs = false
}

tasks.named("jsProcessResources").configure {
    dependsOn(sqliteUnzip)
}