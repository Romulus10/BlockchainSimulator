import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.3.31"
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
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.14.0")
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
