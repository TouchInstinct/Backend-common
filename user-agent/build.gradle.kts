plugins {
    id("kotlin")
}

dependencies {
    implementation(project(":common-device"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.github.ua-parser:uap-java")
}
