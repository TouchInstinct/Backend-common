plugins {
    id("kotlin")
    id("maven-publish")
}

dependencies {
    api("com.fasterxml.jackson.module:jackson-module-kotlin")

    api(project(":common"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("ch.qos.logback.contrib:logback-jackson")
    implementation("ch.qos.logback.contrib:logback-json-classic")
}
