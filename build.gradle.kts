import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    kotlin("jvm")
    id ("org.springframework.boot") apply false

    // IntelliJ
    idea

    // Classes annotated with @Configuration, @Controller, @RestController, @Service or @Repository are automatically opened
    // https://kotlinlang.org/docs/reference/compiler-plugins.html#spring-support
    kotlin("plugin.spring") apply false

    // A Gradle plugin that provides Maven-like dependency management and exclusions
    // https://docs.spring.io/dependency-management-plugin/docs/current/reference/html/
    id("io.spring.dependency-management")
}

allprojects {
    group = "ru.touchin"
    version = "2021.1"

    repositories {
        mavenLocal()
        mavenCentral()
    }

    println("Enabling IDEA plugin in project ${project.name}...")
    apply(plugin = "idea")
}

subprojects {
    println("Enabling Kotlin JVM plugin in project ${project.name}...")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    println("Enabling Kotlin Spring plugin in project ${project.name}...")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    println("Enabling Spring Boot Dependency Management in project ${project.name}...")
    apply(plugin = "io.spring.dependency-management")

    configure<DependencyManagementExtension> {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }

        dependencies {
            dependency("ch.qos.logback:logback-classic:1.2.3")
            dependency("ch.qos.logback.contrib:logback-json-classic:0.1.5")
            dependency("ch.qos.logback.contrib:logback-jackson:0.1.5")

            dependency("org.testcontainers:testcontainers:1.15.1")
            dependency("org.testcontainers:postgresql:1.15.1")
            dependency("org.testcontainers:junit-jupiter:1.15.1")
            dependency("org.junit.jupiter:junit-jupiter-api:5.4.2")
            dependency("org.junit.jupiter:junit-jupiter-params:5.4.2")
            dependency("org.junit.jupiter:junit-jupiter-engine:5.4.2")

            dependency("org.liquibase:liquibase-core:4.4.0")

            dependency("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
            dependency("org.mockito:mockito-inline:3.11.0")

            dependency("com.github.zafarkhaja:java-semver:0.9.0")

            dependency("javax.measure:unit-api:2.1.2")
            dependency("tech.units:indriya:2.1.2")

            dependency("org.locationtech.spatial4j:spatial4j:0.8")

            dependency("com.auth0:java-jwt:3.10.3")
        }
    }

    dependencies {
        // use for @ConstructorBinding
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    }

    tasks.withType<KotlinCompile> {
        java.sourceCompatibility = JavaVersion.VERSION_11
        kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    }

    tasks.test {
        useJUnitPlatform()
    }
}
