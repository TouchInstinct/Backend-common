plugins {
    id("kotlin")
    id("kotlin-spring")
}

dependencies {
    api(project(":spreadsheets"))

    implementation(project(":common"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot")

    implementation("com.google.api-client:google-api-client")
    implementation("com.google.apis:google-api-services-sheets")
    implementation("com.google.auth:google-auth-library-oauth2-http")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
