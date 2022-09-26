plugins {
    id("kotlin")
    id("kotlin-spring")
    kotlin("plugin.jpa")
}

dependencies {
    runtimeOnly("org.postgresql:postgresql")

    api(project(":common"))
    api(project(":common-device"))

    api(project(":common-spring-jpa"))

    api("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.liquibase:liquibase-core")

    implementation("org.springframework.boot:spring-boot-starter-security")

    testImplementation(project(":common-spring-test-jpa"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin")

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:junit-jupiter")
}
