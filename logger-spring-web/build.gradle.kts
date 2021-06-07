plugins {
    id("kotlin")
    id("kotlin-spring")
    id("maven-publish")
}

dependencies {
    api(project(":logger-spring"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation(project(":common-spring-web"))

    implementation("org.springframework.boot:spring-boot-starter-web")
}
