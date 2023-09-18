import java.util.Properties
import java.io.File
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.kotlin.parcelize)
}


val secretsProperties = Properties().apply {
    load(File("secrets.properties").inputStream())
}
val apiKey = secretsProperties["API_KEY"] as String
val baseUrl = secretsProperties["BASE_URL"] as String

android {
    namespace = "it.macgood.wallpaperapp.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_KEY", apiKey)
            buildConfigField("String", "BASE_URL", baseUrl)
        }
        release {
            buildConfigField("String", "API_KEY", apiKey)
            buildConfigField("String", "BASE_URL", baseUrl)
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }
    kotlin {
        jvmToolchain {
            @Suppress("USELESS_CAST")
            this as JavaToolchainSpec // languageVersion inaccessible without cast
            languageVersion.set(JavaLanguageVersion.of(17))
        }
//        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    hilt {
        enableAggregatingTask = true
    }
}

dependencies {

    implementation(libs.kotlin.core)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel)

    implementation(libs.compose.activity)
    implementation(libs.compose.bom)
    implementation(libs.compose.ui.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3.material3)
    implementation(libs.compose.foundation)
    implementation(libs.compose.hilt.navigation)
    implementation(libs.compose.runtime.livedata)
    implementation(libs.compose.runtime)
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.material.icons.ext)
    implementation(libs.compose.accompanist.navigation)
    implementation(libs.compose.accompanist.systemuicontroller)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.compose.ui.test.junit4)
    androidTestImplementation(platform(libs.compose.bom))

    debugImplementation(libs.compose.ui.test.manifest)
    debugImplementation(libs.compose.ui.tooling)
    annotationProcessor(libs.dagger.hilt.compiler)


    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    kapt(libs.dagger.hilt.viewmodel.compiler)
    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)

    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.converter.gson)
    implementation(libs.okhttp.okhttp)
    implementation(libs.retrofit)

    implementation(libs.kotlin.coroutines.android)
    implementation(libs.kotlin.coroutines.core)

    implementation(libs.coil.compose)
    implementation(libs.coil.coil)

    implementation(libs.raamcosta.compose.destinations.animations.core)
    ksp(libs.raamcosta.compose.destinations.ksp)
    implementation(project(":core"))
    implementation(project(":domain"))
}