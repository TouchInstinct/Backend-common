plugins {
    id("kotlin")
    id("kotlin-spring")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":common-spring-security"))

    implementation("org.springframework.security:spring-security-oauth2-jose")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    api("com.fasterxml.jackson.module:jackson-module-kotlin")
}
