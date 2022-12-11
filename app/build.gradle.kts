plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
	//id("com.google.gms.google-services")
}

android {
    signingConfigs {
		create("healthySoul") {
			storeFile = file(".\\keyhealthysoul.jks")
			storePassword = "Mp2630547"
			keyAlias = "keyHealthySoul"
			keyPassword = "Mp2630547"
		}
	}
	compileSdk = 32

    defaultConfig {
        applicationId = "com.gmail.pavlovsv93.healthysoul"
        minSdk = 23
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		signingConfig = signingConfigs.getByName("healthySoul")
	}

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
		debug{
			signingConfig = signingConfigs.getByName("healthySoul")
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
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    //добавление зависимости для модулей
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	implementation("com.google.firebase:firebase-firestore:24.4.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:30.2.0"))
	implementation("com.google.firebase:firebase-firestore-ktx")
    implementation ("com.google.firebase:firebase-database-ktx")
    implementation ("com.google.firebase:firebase-auth:21.1.0")
    implementation ("com.firebaseui:firebase-ui-auth:8.0.2")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    //Koin
    implementation("io.insert-koin:koin-android:3.1.6")
    implementation("io.insert-koin:koin-core:3.1.6")

    //Room
    implementation("androidx.room:room-runtime:2.4.3")
    kapt("androidx.room:room-compiler:2.4.3")

    //Coil
    implementation("io.coil-kt:coil:2.2.2")

    //navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")

    //auto size text and view
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    implementation("com.intuit.ssp:ssp-android:1.1.0")

    //lottie
    implementation("com.airbnb.android:lottie:5.2.0")
}

apply(plugin = "com.google.gms.google-services")