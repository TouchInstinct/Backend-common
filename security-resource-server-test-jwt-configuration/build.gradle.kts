plugins {
    id("kotlin")
    id("kotlin-spring")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":common-spring-security"))

    implementation(project(":security-jwt-common"))

    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
}
