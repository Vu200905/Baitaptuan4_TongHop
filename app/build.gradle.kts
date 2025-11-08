plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose) // Sửa lại từ compose compiler
}

android {
    namespace = "com.example.uthauth"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.uthauth"
        minSdk = 26
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_1_8 // Sửa thành 1_8 cho tương thích rộng
        targetCompatibility = JavaVersion.VERSION_1_8 // Sửa thành 1_8 cho tương thích rộng
    }
    kotlinOptions {
        jvmTarget = "1.8" // Sửa thành 1.8 cho tương thích rộng
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // --- CÁC DÒNG CODE MẶC ĐỊNH ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // --- CÁC DÒNG CODE BẠN CẦN THÊM VÀO ĐÂY ---
    // DataStore Preferences để lưu theme
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // ViewModel cho Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")
}

