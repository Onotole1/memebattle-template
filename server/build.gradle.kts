plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.flywaydb.flyway") version "5.2.4"
    id("java")
    application
}

flyway {
    url = System.getenv("DB_URL")
    user = System.getenv("DB_USER")
    password = System.getenv("DB_PASSWORD")
    baselineOnMigrate = true
    locations = arrayOf("filesystem:resources/db/migration")
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
    implementation("io.ktor:ktor-server-cio:1.2.4")
    implementation("io.ktor:ktor-gson:1.2.4")
    implementation("io.ktor:ktor-auth:1.2.4")
    implementation("io.ktor:ktor-auth-jwt:1.2.4")
    implementation("org.springframework.security:spring-security-crypto:5.1.6.RELEASE")
    implementation("commons-logging:commons-logging:1.2")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.kodein.di:kodein-di-generic-jvm:6.3.3")
    implementation("org.kodein.di:kodein-di-framework-ktor-server-jvm:6.3.3")
    implementation("com.squareup.sqldelight:sqlite-driver:1.2.2")
    implementation("org.apache.tika:tika-parsers:1.11")
    implementation("org.jetbrains.exposed:exposed:0.17.7")
    implementation("com.zaxxer:HikariCP:2.7.8")
    implementation("org.postgresql:postgresql:42.2.2")
    implementation("org.flywaydb:flyway-core:5.2.4")
    testImplementation("io.ktor:ktor-server-tests:1.2.4")
    testImplementation("com.jayway.jsonpath:json-path:2.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
    testImplementation("org.testcontainers:postgresql:1.13.0")
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