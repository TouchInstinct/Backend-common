plugins {
    id("kotlin")
    id("kotlin-spring")
    id("maven-publish")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation(project(":logger-spring"))
    implementation(project(":common-spring-web"))
    implementation(project(":push-message-provider"))

    testImplementation(project(":logger-spring-web"))

    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
}
