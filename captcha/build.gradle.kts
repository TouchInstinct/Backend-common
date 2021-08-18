plugins {
    id("kotlin")
    id("kotlin-spring")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":common-spring-web"))

    implementation(project(":logger-spring"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-aop")
}
