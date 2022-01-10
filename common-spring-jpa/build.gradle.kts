plugins {
    id("kotlin")
    id("kotlin-spring")
    id("maven-publish")
    id("org.jetbrains.kotlin.kapt")
}

dependencies {
    kapt("org.hibernate:hibernate-jpamodelgen")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation(project(":common-spring"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    compileOnly("org.liquibase:liquibase-core")
}
