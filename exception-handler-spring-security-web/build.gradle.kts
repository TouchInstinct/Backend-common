plugins {
    id("kotlin")
    id("kotlin-spring")
    id("maven-publish")
}

dependencies {
    implementation(project(":exception-handler-spring-web"))

    implementation("org.springframework.boot:spring-boot-starter-security")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
