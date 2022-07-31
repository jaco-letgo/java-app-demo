plugins {
    java
    kotlin("jvm") version "1.6.21"
    id("org.springframework.boot") version "2.7.0"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.6.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("info.solidsoft.pitest") version "1.7.0"
}

group = "com.letgo"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.0")
    implementation("org.hibernate:hibernate-core:5.6.5.Final")
    implementation("org.springframework:spring-orm:5.3.16")
    implementation("mysql:mysql-connector-java:8.0.28")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.json:json:20211205")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.reflections:reflections:0.10.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.0")
    testImplementation("io.mockk:mockk:1.12.2")
    testImplementation("com.pinterest:ktlint:0.44.0")
}

tasks {
    test {
        useJUnitPlatform()
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    pitest {
        junit5PluginVersion.set("0.15")
        threads.set(6)
        excludedClasses.addAll("com.letgo.*.infrastructure.configuration.**")
        avoidCallsTo.set(setOf("kotlin.jvm.internal", "kotlinx.coroutines"))
    }
}

noArg {
    annotation("com.letgo.shared.domain.DomainObject")
    invokeInitializers = false
}
