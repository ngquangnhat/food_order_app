plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.newfoodorder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.newfoodorder"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        viewBinding = true
        //noinspection DataBindingWithoutKapt
        dataBinding = true
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Circle Imageview
    implementation (libs.circleimageview)
    // Room database
    implementation (libs.androidx.room.runtime)
    kapt (libs.androidx.room.compiler)
    // Firebase
    implementation (platform(libs.firebase.bom))
    implementation (libs.firebase.database)
    //event bus
    implementation (libs.eventbus)
    // Glide load image
    implementation (libs.glide)
    // Gson
    implementation (libs.gson)
    // Indicator
    implementation (libs.circleindicator)
}