plugins {
    id("kotlin")
    id("kotlin-spring")
}

dependencies {
    api("javax.measure:unit-api")
    api("tech.units:indriya")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}
