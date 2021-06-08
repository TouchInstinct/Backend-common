plugins {
    id("kotlin")
    id("kotlin-spring")
}

dependencies {
    api(project(":common-measure"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}
