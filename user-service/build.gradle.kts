plugins {
    java
    id("org.springframework.boot") version "3.4.7"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.ernieblues"
version = "0.0.1-SNAPSHOT"
description = "demo1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Spring Cloud
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    // Database
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    runtimeOnly("org.postgresql:postgresql")

    // API documentation
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.9")

    // Lombok
    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")

    testAnnotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2024.0.1")
    }
}
