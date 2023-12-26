plugins {
  id("com.android.application")
}

android {
  namespace = "com.example.teenduh"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.example.teenduh"
    minSdk = 19
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
}

dependencies {

  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("com.google.android.material:material:1.11.0")
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  implementation("androidx.navigation:navigation-fragment:2.7.5")
  implementation("androidx.navigation:navigation-ui:2.7.5")

  // testing
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")

  // packaging
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")

  // loading animation
  coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")
  implementation("com.airbnb.android:lottie:6.2.0")

  //map
  implementation("com.google.android.gms:play-services-maps:18.2.0")
  implementation("com.google.android.gms:play-services-location:21.0.1")
  implementation("com.google.maps:google-maps-services:2.2.0")
  implementation("org.slf4j:slf4j-simple:2.0.9");
  implementation("com.google.maps.android:android-maps-utils:3.8.0");
  implementation("com.google.android.libraries.places:places:2.6.0")

  //firebase
  implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
  implementation("com.google.firebase:firebase-auth:22.3.0")
  implementation("com.google.firebase:firebase-firestore:24.10.0")
  implementation("com.google.android.gms:play-services-auth:20.7.0")
}