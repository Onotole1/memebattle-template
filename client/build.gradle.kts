plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

repositories {
    google()
    jcenter()
}

android {
    compileSdkVersion(29)
    buildToolsVersion = "29.0.3"
    defaultConfig {
        applicationId = "ru.memebattle"
        minSdkVersion(23)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.61")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.navigation:navigation-fragment-ktx:2.2.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.2.1")
    implementation("com.squareup.retrofit2:retrofit:2.7.1")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("com.google.android.material:material:1.1.0")
    implementation("androidx.recyclerview:recyclerview:1.2.0-alpha01")
    implementation("io.reactivex.rxjava2:rxkotlin:2.3.0")
    implementation("io.reactivex.rxjava2:rxjava:2.2.9")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("com.squareup.retrofit2:retrofit:2.7.1")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.3.1")
    implementation("androidx.room:room-runtime:2.2.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    kapt("androidx.room:room-compiler:2.2.4")
    implementation("androidx.room:room-rxjava2:2.2.4")
    implementation("org.koin:koin-android:2.0.1")
    implementation("org.koin:koin-android-viewmodel:2.0.1")
    implementation("org.koin:koin-androidx-scope:2.0.1")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("com.google.firebase:firebase-core:17.2.3")
    implementation("com.google.firebase:firebase-messaging:20.1.1")
    implementation("com.github.nkzawa:socket.io-client:0.6.0")
    implementation(project(":common"))
}
