plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.pablo.recordatorio.medico"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pablo.recordatorio.medico"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "BASE_URL", "\"http://192.168.100.5:8080/gateway/api\"") // URL de desarrollo
        }
        getByName("release") {
            buildConfigField("String", "BASE_URL", "\"http://45.33.13.164:8080/gateway/api\"") // URL de producción
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true // Habilitar campos personalizados de BuildConfig
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    debugImplementation("com.example.my_flutter_module:flutter_debug:1.0.7")
    releaseImplementation("com.example.my_flutter_module:flutter_debug:1.0.7")
}