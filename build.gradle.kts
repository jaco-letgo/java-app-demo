plugins {
    idea
    java
    `java-test-fixtures`
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
    implementation("org.springframework:spring-orm:5.3.20")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("org.reflections:reflections:0.10.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    runtimeOnly("mysql:mysql-connector-java:8.0.29")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.0")
    testImplementation("io.mockk:mockk:1.12.4")
    testImplementation("com.pinterest:ktlint:0.45.2")
}

testing {
    suites {
        val testFunctional by registering(JvmTestSuite::class) {
            testType.set(TestSuiteType.FUNCTIONAL_TEST)
            idea.module { testSourceDirs.addAll(sourceSets[this@registering.name].allSource.srcDirs) }

            dependencies {
                implementation(project)
                implementation(project.dependencies.testFixtures(project))
                implementation("org.springframework.boot:spring-boot-starter-web:2.7.0")
                implementation("org.springframework.boot:spring-boot-starter-test:2.7.0")
                implementation("io.cucumber:cucumber-java:7.0.0")
                implementation("io.cucumber:cucumber-junit:7.0.0")
                implementation("io.cucumber:cucumber-spring:7.4.1")
                implementation("io.cucumber:cucumber-junit-platform-engine:7.3.3")
                implementation("org.junit.platform:junit-platform-suite:1.8.2")
                implementation("io.rest-assured:kotlin-extensions:5.1.1")
            }

            targets {
                all {
                    testTask.configure {
                        systemProperties["cucumber.publish.quiet"] = "true"
//                        systemProperties["cucumber.execution.parallel.enabled"] = "true"
                    }
                }
            }
        }
    }
}

tasks {
    test {
        useJUnitPlatform()
    }

    check {
        dependsOn(project.testing.suites)
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
