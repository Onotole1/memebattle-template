plugins {
    id("java-library")
    id("kotlin")
    kotlin("jvm")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.61")
}
repositories {
    jcenter()
}