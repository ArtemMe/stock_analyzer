import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.3.RELEASE"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"
}

group = "com.artem.mezh"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencyManagement {
    val springCloudVersion = "Hoxton.SR9"
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:Hoxton.SR7")
    }
}

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("com.github.tomakehurst:wiremock:2.27.2")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.telegram:telegrambots-spring-boot-starter:4.9.1")

    //tests
	testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner:3.0.0")
    testImplementation ("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
    testImplementation("com.google.guava:guava:28.1-jre")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
