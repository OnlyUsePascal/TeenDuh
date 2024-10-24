plugins {
  id("com.android.application")
  id("com.google.gms.google-services")
}

android {
  namespace = "com.example.teenduh"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.example.teenduh"
    minSdk = 26
    targetSdk = 34
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
    }
}

dependencies {
  implementation("com.google.android.gms:play-services-wallet:19.3.0-beta01")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("com.google.android.material:material:1.11.0")
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  implementation("androidx.navigation:navigation-fragment:2.7.5")
  implementation("androidx.navigation:navigation-ui:2.7.5")
  implementation("androidx.annotation:annotation:1.8.0")
  implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
  implementation("pl.bclogic:pulsator4droid:1.0.3")
  implementation("de.hdodenhof:circleimageview:3.1.0")

  // auth
  implementation("com.google.android.gms:play-services-auth:20.7.0")
//  implementation("com.facebook.android:facebook-login:latest.release")
  implementation("com.squareup.okhttp3:okhttp:4.10.0")

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
  implementation("com.google.firebase:firebase-analytics:21.5.0")
  implementation("com.firebaseui:firebase-ui-firestore:8.0.2")
  implementation("com.google.firebase:firebase-firestore-ktx:24.10.0")
  implementation("com.google.firebase:firebase-storage-ktx:20.3.0")
  implementation("com.google.firebase:firebase-messaging-ktx:23.4.0")
  implementation("com.google.firebase:firebase-messaging:23.4.0")
  implementation("com.google.firebase:firebase-storage:20.3.0")

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
  implementation("com.stripe:stripe-android:20.36.1")

}