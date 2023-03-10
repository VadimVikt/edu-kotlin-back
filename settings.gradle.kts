rootProject.name = "edu-kotlin-back"

pluginManagement {
    val kotlinVersion: String by settings
    val kotestVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        id("io.kotest.multiplatform") version kotestVersion apply false
    }
}

include("m1l1-quickstart")
include("m2-prepare")
