plugins {
    kotlin("jvm") version "1.9.22"
    application
}

group = "com.tugas2"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

application {
    mainClass.set("NewsFeedSimulatorKt")
}

kotlin {
    jvmToolchain(17)
}
