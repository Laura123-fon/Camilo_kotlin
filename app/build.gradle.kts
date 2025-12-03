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

    // --- SOLUCIÓN AL ERROR DE MERGE DE RECURSOS ---
    // Configura cómo se empaquetan los archivos JAR en el APK de prueba.
    packagingOptions{
        resources {
            // Excluye archivos de metadatos (licencias y avisos) que se duplican
            // en las dependencias de testing (especialmente JUnit y MockK).
            excludes += "/META-INF/{AL2.0,LGPL2.1}" // Exclusiones comunes para resolver conflictos de licencias
            excludes += "META-INF/LICENSE.md" // Archivo específico que causó el error
            excludes += "META-INF/LICENSE-notice.md"
            excludes += "META-INF/LICENSE"
        }
    }
    // ---------------------------------------------
}

// Bloque de dependencias
dependencies {

    // --- DEPENDENCIAS CORE Y KOTLIN ---
    implementation(libs.androidx.core.ktx) // Funciones de extensión de Kotlin para Android
    implementation(libs.kotlinx.coroutines.android) // Implementación de Coroutines para Android

    // --- DEPENDENCIAS COMPOSE FUNDAMENTALES ---
    // Usar la BOM (Bill of Materials) para gestionar versiones de Compose de forma coherente
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose) // Integración de Activity con Compose
    implementation(libs.androidx.compose.ui) // Toolkit base de Compose UI
    implementation(libs.androidx.compose.ui.graphics) // Gráficos base
    implementation(libs.androidx.compose.ui.tooling.preview) // Herramientas para previsualización
    implementation(libs.androidx.compose.material3) // Implementación de Material Design 3
    implementation(libs.androidx.compose.material3.window.size.class1) // Para diseño adaptable
    implementation(libs.androidx.compose.material.icons.extended) // Iconos adicionales

    // --- DEPENDENCIAS DE ARQUITECTURA (LIFECYCLE, NAVIGATION) ---
    implementation(libs.androidx.lifecycle.runtime.ktx) // Soporte de Coroutines para Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.compose) // ViewModel Integration para Compose
    implementation(libs.androidx.lifecycle.runtime.compose) // Para observar estados de Lifecycle
    implementation(libs.androidx.navigation.compose) // Navegación entre composables

    // --- DEPENDENCIAS DE PERSISTENCIA Y DATOS ---
    // Room Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx) // Extensiones de Kotlin para Room
    ksp(libs.androidx.room.compiler) // KSP para la generación de código de Room

    // DataStore Preferences
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences)

    // --- DEPENDENCIAS DE RED (ASUMIDAS POR LA MAQUETA ORIGINAL) ---
    implementation(libs.coil.compose) // Carga de imágenes (asumida)
    implementation(libs.retrofit) // Cliente HTTP principal
    implementation(libs.retrofit.converter.gson) // Convertidor JSON
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0") // Interceptor para logs de red

    // =================================================================
    // --- DEPENDENCIAS DE TESTING ---
    // =================================================================

    // 1. UNIT TESTING (Pruebas de lógica pura)
    testImplementation("junit:junit:4.13.2") // JUnit 4 Core
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0") // Coroutines Test
    testImplementation("androidx.arch.core:core-testing:2.2.0") // Lifecycle/Arch Test

    // 2. UI TESTING (Pruebas de interfaz de usuario con Compose - src/androidTest)
    androidTestImplementation("io.mockk:mockk-android:1.13.10") // Mocking for Android

    // Infraestructura de Compose Test
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4) // Incluye 'isA'
    androidTestImplementation(libs.androidx.junit) // AndroidX JUnit extensions
    androidTestImplementation(libs.androidx.espresso.core) // Opcional, pero común

    // Manifest Test: Necesario para que las herramientas de UI Test funcionen en el entorno de desarrollo
    debugImplementation(libs.androidx.compose.ui.test.manifest) // Resuelve errores si faltaba
    debugImplementation(libs.androidx.compose.ui.tooling) // Herramientas de depuración
}