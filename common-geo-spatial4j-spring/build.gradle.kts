plugins {
    id("kotlin")
    id("kotlin-spring")
}

dependencies {
    api(project(":common-geo"))
    api("org.locationtech.spatial4j:spatial4j")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot")
}
