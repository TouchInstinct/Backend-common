plugins {
    id("kotlin")
    id("kotlin-spring")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-autoconfigure")

    implementation(project(":logger"))
    implementation(project(":push-message-provider"))
}
