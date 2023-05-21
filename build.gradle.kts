import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
//    id("org.graalvm.buildtools.native") version "0.9.20"
    id("org.openapi.generator") version "6.2.1"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.8.20"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.8.20"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"
    kotlin("kapt") version "1.3.72"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

//    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4")


    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("javax.validation:validation-api:2.0.1.Final")

    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.9.0")

    implementation("org.jetbrains.kotlin:kotlin-scripting-common")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm")
    implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies")
    implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies-maven")

    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.flywaydb.flyway-test-extensions:flyway-spring-test:7.0.0")
    compileOnly("jakarta.servlet:jakarta.servlet-api:5.0.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
    testImplementation("com.github.tomakehurst:wiremock-jre8-standalone:2.35.0")
    testImplementation("org.springframework.security:spring-security-test:6.1.0")
    testImplementation("io.zonky.test:embedded-database-spring-test:2.3.0")
    testImplementation("io.zonky.test:embedded-postgres:2.0.4")
    implementation(kotlin("script-runtime"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

openApiGenerate {
    generatorName.set("spring")
    inputSpec.set("${projectDir}/src/main/resources/api-spec.yml")
    outputDir.set("$buildDir/generated")
    configFile.set("${projectDir}/src/main/resources/api-config.json")
    modelNamePrefix.set("web")
    configOptions.set(mapOf("useTags" to "true"))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn("openApiGenerate")
    kotlinOptions.jvmTarget = "17"
}

sourceSets {
    main {
        kotlin {
            srcDir("$buildDir/generated/src/main/java")
        }
        java {
            srcDir("$buildDir/generated/src/main/java")
        }
    }
}