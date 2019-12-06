import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.3.31"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.3.50"
    idea
}

application {
    mainClassName = "edu.drexel.se575.BlockchainSimulatorKt"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("io.javalin:javalin:3.6.0")
    implementation("org.jetbrains.kotlin:kotlin-serialization:1.3.31")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.14.0")
    implementation("org.slf4j:slf4j-simple:1.8.0-beta4")
    compile("com.fasterxml.jackson.core:jackson-databind:2.10.0")

}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

// config JVM target to 1.8 for kotlin compilation tasks
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}
