@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.smarthive"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.smarthive"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Firebase BOM and specific Firebase services
    implementation(libs.firebase.bom)
    implementation(libs.play.services.auth)
    implementation(libs.com.google.firebase.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.analytics)




    // AndroidX libraries
    implementation(libs.androidx.appcompat.v170)
    implementation(libs.material.v1120)
    implementation(libs.androidx.recyclerview.v131)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v121)
    androidTestImplementation(libs.androidx.espresso.core.v361)

    implementation (libs.play.services.auth)

    implementation(platform(libs.firebase.bom))
    implementation(platform(libs.kotlin.bom))

    // Firebase Authentication
    implementation(libs.google.firebase.auth)

    // Firebase Firestore
    implementation(libs.firebase.firestore.ktx)
    implementation (platform(libs.firebase.bom.v3223))
    implementation (libs.play.services.auth.v2060)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.stdlib.jdk8)
}
