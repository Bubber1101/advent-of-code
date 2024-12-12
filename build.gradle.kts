plugins {
    kotlin("jvm") version "2.0.20"
    application
}

group = "com.bubber.aoc"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}

application {
    mainClass = "com.bubber.aoc.MainKt"
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}