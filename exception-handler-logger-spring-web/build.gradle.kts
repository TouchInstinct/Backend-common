plugins {
    id("kotlin")
    id("kotlin-spring")
    id("maven-publish")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation(project(":common-spring"))
    implementation(project(":logger-spring"))
    implementation(project(":exception-handler-spring-web"))
}
