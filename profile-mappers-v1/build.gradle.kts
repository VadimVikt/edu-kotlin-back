plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":profile-openapi-v1-jackson"))
    implementation(project(":profile-common"))

    testImplementation(kotlin("test-junit"))
}