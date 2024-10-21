import java.net.URI

pluginManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()

  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
//    maven("https://jitpack.io")
//    jcenter()
    maven{
      setUrl("https://jitpack.io")
    }
    maven {
      url = uri("https://cardinalcommerceprod.jfrog.io/artifactory/android")
      credentials{
        username = "braintree_team_sdk"
        password ="AKCp8jQcoDy2hxSWhDAUQKXLDPDx6NYRkqrgFLRc3qDrayg6rrCbJpsKKyMwaykVL8FWusJpp"
      }
    }
  }
}

rootProject.name = "TeenDuh"
include(":app")
