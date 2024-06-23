@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication)
    kotlin("kapt")
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.secretsGradlePlugin)
    alias(libs.plugins.googleServices)
}

android {
    namespace = "com.example.zebracoffee"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.zebracoffee"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildFeatures {
            buildConfig = true
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.android.composeUi)
    implementation(libs.android.composeMaterial3)
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    implementation("androidx.compose.material3:material3")
//    implementation("androidx.compose.material:material")
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.viewpager2)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    //splash screen
    implementation(libs.androidx.core.splashscreen)
    //navigation
    implementation(libs.androidx.navigation.compose)
    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.gson)
    // Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.work)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
    //okHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    //glide
    implementation(libs.compose)
    // Lifecycle
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.runtime.compose)
    //lottie
    implementation(libs.android.lottie.compose)
    implementation(libs.androidx.appcompat)
    //permission
    implementation(libs.accompanist.permissions)
    implementation(libs.play.services.location)
    //room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    annotationProcessor(libs.room.compiler)
    kapt(libs.androidx.room.room.compiler2)
    // optional - Paging 3 Integration
    implementation(libs.room.paging)
    implementation(libs.android.composeUi)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    //Paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.kotlinx.serialization.json)
    //ConstraintLayout in Compose
    implementation(libs.androidx.constraintlayout.compose)
    //data store
    implementation(libs.androidx.datastore.preferences)
    //gson
    implementation(libs.gson)
    //Google Services & Maps
    implementation(libs.play.services.location)
    implementation(libs.maps.compose)
    implementation(libs.play.services.maps)
    implementation(libs.gms.play.services.maps)
    implementation(libs.maps.ktx)
    //Accompanist (Permission)
    implementation(libs.accompanist.permissions)
    //exo-player
    implementation(libs.exoplayer)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    //glide
    implementation(libs.compose)
    //coil
    implementation(libs.coil.compose)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidsvg)
    //Chucker
    debugImplementation(libs.library)
    releaseImplementation(libs.library.no.op)
    //Pull Refresh
    implementation(libs.androidx.material)
    implementation(libs.accompanist.swiperefresh)
    //clustering
    implementation(libs.android.maps.utils)
    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebaseAnalytics)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.firebaseMessaging)
    //Accompanist System UI Controller Deprecated
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.activity.compose)
    //Google SMS-Retriever
    implementation (libs.play.services.auth)
    implementation (libs.play.services.auth.api.phone)
    //LeakCanary
//    debugImplementation ("com.squareup.leakcanary:leakcanary-android:2.9.1")
    implementation (libs.android.maps.utils)
    implementation (libs.maps.utils.ktx)

}
