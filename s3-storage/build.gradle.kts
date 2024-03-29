plugins {
    id("kotlin")
    id("kotlin-spring")
    id("maven-publish")
}

dependencies {
    api(project(":common"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot")

    implementation("software.amazon.awssdk:s3")
}
