plugins {
    kotlin("jvm")
}

dependencies {
    val rabbitVersion: String by project
    val jacksonVersion: String by project
    val logbackVersion: String by project
    val coroutinesVersion: String by project
    val testContainersVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("com.rabbitmq:amqp-client:$rabbitVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // transport models common
    implementation(project(":profile-common"))

    // v1 api
    implementation(project(":profile-openapi-v1-jackson"))
    implementation(project(":profile-mappers-v1"))

    // Stubs
    implementation(project(":profile-stub"))
    implementation("org.testng:testng:7.1.0")

    testImplementation("org.testcontainers:rabbitmq:$testContainersVersion")
    testImplementation(kotlin("test"))
}
