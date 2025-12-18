// Importaciones de Plugins necesarias para el proyecto
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp) // Plugin para procesadores de anotación como Room
}

// Bloque de configuración general de Android
android {
    namespace = "com.example.app_definida"
    compileSdk = 36 // Versión de SDK para compilar

    defaultConfig {
        applicationId = "com.example.app_definida"
        minSdk = 24 // SDK Mínimo compatible
        targetSdk = 36 // SDK Objetivo
        versionCode = 1
        versionName = "1.0"

        // Runner para las pruebas instrumentadas (AndroidX Test Runner es estándar)
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        // Habilitar el soporte para Compose
        compose = true
    }

    packagingOptions{
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}" 
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
            excludes += "META-INF/LICENSE"
        }
    }
}

// Bloque de dependencias
dependencies {

    // --- DEPENDENCIAS CORE Y KOTLIN ---
    implementation(libs.androidx.core.ktx) 
    implementation(libs.kotlinx.coroutines.android) 

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose) 
    implementation(libs.androidx.compose.ui) 
    implementation(libs.androidx.compose.ui.graphics) 
    implementation(libs.androidx.compose.ui.tooling.preview) 
    implementation(libs.androidx.compose.material3) 
    implementation(libs.androidx.compose.material3.window.size.class1) 
    implementation(libs.androidx.compose.material.icons.extended) 

    implementation(libs.androidx.lifecycle.runtime.ktx) 
    implementation(libs.androidx.lifecycle.viewmodel.compose) 
    implementation(libs.androidx.lifecycle.runtime.compose) 
    implementation(libs.androidx.navigation.compose) 

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx) 
    ksp(libs.androidx.room.compiler) 

    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.coil.compose) 
    implementation(libs.retrofit) 
    implementation(libs.retrofit.converter.gson) 
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0") 

    // =================================================================
    // --- DEPENDENCIAS DE TESTING REPARADAS ---
    // =================================================================

    // 1. UNIT TESTING (src/test)
    testImplementation("junit:junit:4.13.2") 
    testImplementation("io.mockk:mockk:1.13.10") // ¡Añadido para Unit Tests!
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0") 
    testImplementation("androidx.arch.core:core-testing:2.2.0") 
    testImplementation("app.cash.turbine:turbine:1.1.0") // Para testear Flows

    // 2. UI TESTING (src/androidTest)
    androidTestImplementation("io.mockk:mockk-android:1.13.10") 
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4) 
    androidTestImplementation(libs.androidx.junit) 
    androidTestImplementation(libs.androidx.espresso.core) 

    debugImplementation(libs.androidx.compose.ui.test.manifest) 
    debugImplementation(libs.androidx.compose.ui.tooling) 
}
