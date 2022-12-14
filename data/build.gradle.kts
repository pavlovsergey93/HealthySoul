plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("kotlin-kapt")
}

android {
	compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    //добавление зависимости для модулей
    implementation(project(":domain"))

	//Firebase
	implementation(platform("com.google.firebase:firebase-bom:30.2.0"))
	implementation("com.google.firebase:firebase-firestore-ktx")
    implementation ("com.google.firebase:firebase-database-ktx")
    implementation ("com.firebaseui:firebase-ui-auth:8.0.2")

	//Coroutines
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
	implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.6.1")
	implementation("com.google.firebase:firebase-firestore:24.4.0")
    implementation("androidx.room:room-common:2.4.3")
    implementation("androidx.room:room-ktx:2.4.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}