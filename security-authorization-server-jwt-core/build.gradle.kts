plugins {
    id("kotlin")
    id("kotlin-spring")
    kotlin("plugin.jpa")
}

dependencies {
    implementation(project(":security-authorization-server-core"))
    implementation(project(":security-resource-server-jwt-configuration"))
    implementation(project(":security-jwt-common"))

    implementation("com.auth0:java-jwt")
    implementation("org.springframework.security:spring-security-oauth2-jose")
}
