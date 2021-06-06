plugins {
    id("kotlin")
    id("kotlin-spring")
    id("maven-publish")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    compileOnly(project(":common-spring-jpa"))
    compileOnly(project(":common-spring-test"))

    compileOnly("org.springframework.boot:spring-boot-starter-data-jpa")
    compileOnly("org.springframework.boot:spring-boot-starter-test")

    compileOnly("org.testcontainers:testcontainers")
    compileOnly("org.testcontainers:postgresql")
    compileOnly("org.testcontainers:junit-jupiter")
    compileOnly("org.junit.jupiter:junit-jupiter-api")
    compileOnly("org.junit.jupiter:junit-jupiter-params")
}
