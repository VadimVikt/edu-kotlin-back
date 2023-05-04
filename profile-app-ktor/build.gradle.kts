val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val serializationVersion: String by project

plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
}

group = rootProject.group //"ru.vadim.edu.kotlin.back"
version = rootProject.version //"0.0.1"
application {
    mainClass.set("ru.vadim.edu.kotlin.back.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
    testImplementation("io.ktor:ktor-server-test-host-jvm:2.2.4")
    implementation(project(":profile-openapi-v1-jackson"))
    implementation(project(":profile-common"))
    implementation(project(":profile-mappers-v1"))
    implementation(project(":profile-stub"))
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
}

//tasks {
//    val dockerJvmDockerfile by creating(Dockerfile::class) {
//        group = "docker"
//        from("openjdk:17")
//        copyFile("app.jar", "app.jar")
//        entryPoint("java", "-Xms256m", "-Xmx512m", "-jar", "/app.jar")
//    }
//    create("dockerBuildJvmImage", DockerBuildImage::class) {
//        group = "docker"
//        dependsOn(dockerJvmDockerfile, named("jvmJar"))
//        doFirst {
//            copy {
//                from(named("jvmJar"))
//                into("${project.buildDir}/docker/app.jar")
//            }
//        }
//        images.add("${project.name}:${project.version}")
//    }
//}
