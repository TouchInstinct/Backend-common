plugins {
    id("kotlin")
    id("kotlin-spring")
    id("maven-publish")
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}
