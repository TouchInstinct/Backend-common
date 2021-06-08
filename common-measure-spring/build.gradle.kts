plugins {
    id("kotlin")
    id("kotlin-spring")
}

dependencies {
    api(project(":common-measure"))

    implementation("org.springframework.boot:spring-boot")
}
