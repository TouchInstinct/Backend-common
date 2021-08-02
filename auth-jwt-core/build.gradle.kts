plugins {
    id("kotlin")
    id("kotlin-spring")
    kotlin("plugin.jpa")
}

dependencies {
    implementation(project(":auth-core"))
    implementation(project(":auth-jwt"))

    implementation("com.auth0:java-jwt")
    implementation("org.springframework.security:spring-security-oauth2-jose")
}
