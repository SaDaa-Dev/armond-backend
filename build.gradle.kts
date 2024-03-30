plugins {
	java
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	// querydsl 추가
	id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
}

group = "com.chandev"
version = "0.0.1-SNAPSHOT"

val queryDslVersion = "5.0.0"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5")

	// querydsl
	implementation("com.querydsl:querydsl-jpa:${queryDslVersion}:jakarta")
	implementation("com.querydsl:querydsl-sql:${queryDslVersion}")
	annotationProcessor("com.querydsl:querydsl-apt:${queryDslVersion}:jakarta")

	compileOnly("org.projectlombok:lombok")
//	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

val querydslDir = "$buildDir/generated/querydsl"

querydsl {
	jpa = true
	querydslSourcesDir = querydslDir
}
sourceSets.getByName("main") {
	java.srcDir(querydslDir)
}
configurations {
	named("querydsl") {
		extendsFrom(configurations.compileClasspath.get())
	}
}
tasks.withType<com.ewerk.gradle.plugins.tasks.QuerydslCompile> {
	options.annotationProcessorPath = configurations.querydsl.get()
}