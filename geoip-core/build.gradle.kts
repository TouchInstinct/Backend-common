plugins {
    id("kotlin")
    id("kotlin-spring")
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":common-territories"))
    implementation(project(":common-spring-jpa"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

tasks.bootJar {
    enabled = false
}
