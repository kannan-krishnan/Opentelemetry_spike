plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.orane.opentelemetryspike"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.orane.opentelemetryspike"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
//    implementation  ('libs/opentelemetry-javaagent-all.jar')
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")


    // app core infra
//    implementation ("io.reactivex.rxjava2:rxjava:2.2.21")
//    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
//    implementation ("com.akaita.java:rxjava2-debug:1.4.0")

    implementation ("io.opentelemetry:opentelemetry-api:1.7.0")
    implementation ("io.opentelemetry:opentelemetry-sdk:1.7.0")
    implementation ("io.opentelemetry:opentelemetry-exporter-otlp:1.7.0")  // Choose an exporter
//    implementation ("io.opentelemetry:opentelemetry-exporter-otlp-grpc:1.0.0")
//    implementation ("io.opentelemetry:opentelemetry-semconv:1.24.0-alpha")
//    implementation("io.opentelemetry.android:instrumentation:0.1.0-alpha")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")



}