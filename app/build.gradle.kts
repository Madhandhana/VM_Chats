plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.vmchats"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.vmchats"
        minSdk = 24
        targetSdk = 34
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
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.firestore)
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-auth")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.cpp)
    implementation("com.firebaseui:firebase-ui-firestore:8.0.2")
    implementation ("com.github.dhaval2404:imagepicker:2.1")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("androidx.biometric:biometric:1.1.0")
implementation ("com.google.android.material:material:1.9.0")
    implementation ("androidx.core:core:1.7.0")
    implementation ("androidx.room:room-runtime:2.5.0")
    annotationProcessor ("androidx.room:room-compiler:2.5.0")
    implementation ("com.google.android.gms:play-services-base:18.0.1")
}