rootProject.name = "edu-kotlin-back"

pluginManagement {
    val kotlinVersion: String by settings
    val kotestVersion: String by settings
    val openapiVersion: String by settings
    val ktorPluginVersion: String by settings
    val bmuschkoVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        id("io.kotest.multiplatform") version kotestVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
        id("io.ktor.plugin") version ktorPluginVersion apply false
        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
    }
}

//include("m1l1-quickstart")
//include("m2-prepare")
include("profile-openapi-v1-jackson")
include("profile-common")
include("profile-mappers-v1")
include("profile-app-ktor")
include("profile-stub")
