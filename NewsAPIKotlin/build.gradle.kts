plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "me.meer1"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    implementation("org.json:json:20210307")
    implementation ("com.squareup.okhttp3:okhttp:4.9.1") // Use the latest version
    implementation ("com.google.code.gson:gson:2.8.8")   // Use the latest version

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}