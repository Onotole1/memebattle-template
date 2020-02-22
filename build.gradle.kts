import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.squareup.sqldelight:gradle-plugin:1.2.2")
    }
}

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.61"
}

repositories {
    jcenter()
}

val compileKotlin: KotlinCompile by tasks
val compileTestKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}