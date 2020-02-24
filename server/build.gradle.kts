plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm")
    id("com.squareup.sqldelight")
    application
}

application {
    mainClassName = "ru.memebattle.ApplicationKt"
}

group = "ru.memebattle"
version = "1.0-SNAPSHOT"

tasks.withType<Test> {
    useJUnitPlatform()
}

sqldelight {
    database("Database") {
        packageName = "ru.memebattle"
        sourceFolders = listOf("sqldelight")
    }
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