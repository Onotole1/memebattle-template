plugins {
    id("org.jetbrains.kotlin.jvm")
    id("java")
    application
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks

compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

application {
    mainClassName = "ru.memebattle.ApplicationKt"
}

group = "ru.memebattle"
version = "1.0-SNAPSHOT"

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.ktor:ktor-gson:1.2.4")
    implementation("io.ktor:ktor-auth:1.2.4")
    implementation("io.ktor:ktor-server-cio:1.2.4")
    implementation("io.ktor:ktor-websockets:1.2.4")
    implementation("io.ktor:ktor-auth-jwt:1.2.4")
    implementation("commons-logging:commons-logging:1.2")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.kodein.di:kodein-di-generic-jvm:6.3.3")
    implementation("org.kodein.di:kodein-di-framework-ktor-server-jvm:6.3.3")
    testImplementation("io.ktor:ktor-server-tests:1.2.4")
    testImplementation("com.jayway.jsonpath:json-path:2.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
    implementation(project(":common"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}

repositories {
    jcenter()
}

tasks.register("stage") {
    dependsOn("installDist")
}