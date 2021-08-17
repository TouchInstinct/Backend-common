plugins {
    id("kotlin")
    id("kotlin-spring")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":common-spring-security"))

    implementation(project(":security-authorization-server-jwt-core"))

    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    api("com.fasterxml.jackson.module:jackson-module-kotlin")
}
