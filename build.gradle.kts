buildscript {
    val kotlin_version by extra("2.0.21")
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
    repositories {
        mavenCentral()
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  id("com.android.application") version "8.7.2" apply false
  id("com.google.gms.google-services") version "4.4.2" apply false
}

