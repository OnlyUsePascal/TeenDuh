plugins {
  id("com.android.application")
  id("com.google.gms.google-services")
}

android {
  namespace = "com.example.teenduh"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.example.teenduh"
    minSdk = 26
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    System.getenv("MY_VARIABLE_NAME")
    resValue("string", "web_client_id", properties["web_client_id"]?.toString() ?: "what1")
    resValue("string", "web_client_secret", properties["web_client_secret"]?.toString() ?: "what2")
    resValue("string", "cloud_mess_key", properties["cloud_mess_key"]?.toString() ?: "what3")
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
    buildFeatures {
        viewBinding = true
      compose = true
    }
}

dependencies {
  implementation("com.google.android.gms:play-services-gcm:17.0.0")
  implementation("com.google.android.gms:play-services-wallet:19.4.0")
  implementation("androidx.appcompat:appcompat:1.7.0")
  implementation("com.google.android.material:material:1.12.0")
  implementation("androidx.constraintlayout:constraintlayout:2.2.0")
  implementation("androidx.navigation:navigation-fragment:2.8.4")
  implementation("androidx.navigation:navigation-ui:2.8.4")
  implementation("androidx.annotation:annotation:1.9.1")
  implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
  implementation("pl.bclogic:pulsator4droid:1.0.3")
  implementation("de.hdodenhof:circleimageview:3.1.0")

  implementation("androidx.compose.ui:ui:1.6.1")
  implementation("androidx.compose.ui:ui-tooling:1.6.1")
  implementation("androidx.compose.material:material:1.6.1")
  implementation("androidx.compose.runtime:runtime:1.6.1")

  // auth
  implementation("com.google.android.gms:play-services-auth:21.2.0")
//  implementation("com.facebook.android:facebook-login:latest.release")
  implementation("com.squareup.okhttp3:okhttp:4.12.0")
  implementation("androidx.activity:activity:1.8.0")
  implementation("androidx.core:core-ktx:1.15.0")

  // testing
  androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.2.1")

  // packaging
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")

  // loading animation
  coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.3")
  implementation("com.airbnb.android:lottie:6.2.0")

  //map
  implementation("com.google.android.gms:play-services-maps:19.0.0")
  implementation("com.google.android.gms:play-services-location:21.3.0")
  implementation("com.google.maps:google-maps-services:2.2.0")
  implementation("org.slf4j:slf4j-simple:2.0.9")
  implementation("com.google.maps.android:android-maps-utils:3.8.0")
  implementation("com.google.android.libraries.places:places:4.1.0")

  //firebase
  implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
  implementation("com.google.firebase:firebase-auth:23.1.0")
  implementation("com.google.firebase:firebase-firestore:25.1.1")
  implementation("com.google.firebase:firebase-analytics:22.1.2")
  implementation("com.firebaseui:firebase-ui-firestore:8.0.2")
  implementation("com.google.firebase:firebase-firestore-ktx:25.1.1")
  implementation("com.google.firebase:firebase-storage-ktx:21.0.1")
  implementation("com.google.firebase:firebase-messaging-ktx:24.1.0")
  implementation("com.google.firebase:firebase-messaging:24.1.0")
  implementation("com.google.firebase:firebase-storage:21.0.1")

  // international phone number hadling
  implementation("com.fredporciuncula:phonemoji:1.5.2")

  //drawing
  implementation("com.makeramen:roundedimageview:2.3.0")
  implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.23")
  implementation ("com.github.dhaval2404:imagepicker:2.1")
  implementation ("com.github.yuyakaido:CardStackView:v2.3.4")
  implementation ("com.squareup.picasso:picasso:2.71828")
  implementation("com.lorentzos.swipecards:library:1.0.9")
  implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

  // payment
  // implementation("com.braintreepayments.api:braintree:3.21.1")
  implementation("com.braintreepayments.api:drop-in:6.14.0")
  implementation("org.jfrog.cardinalcommerce.gradle:cardinalmobilesdk:2.2.7-5")
  implementation("com.stripe:stripe-android:20.53.0")

}
java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(17)
  }
}
