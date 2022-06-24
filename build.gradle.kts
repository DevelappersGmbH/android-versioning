plugins {
    kotlin("jvm") version "1.7.0"

    `java-gradle-plugin`
    `maven-publish`

    id("com.gradle.plugin-publish") version "0.21.0"
}

group = "de.develappers"
version = "1.1.8"

repositories {
    google()
    mavenCentral()
    jcenter()
}

dependencies {
    compileOnly("com.android.tools.build:gradle:7.2.1")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

gradlePlugin {
    plugins {
        create("versioningPlugin") {
            id = "de.develappers.versioning"
            displayName = "Versioning plugin for android projects"
            description = "Provides tasks for bumping version number of an android project."
            implementationClass = "de.develappers.versioning.Versioning"
        }
    }
}

pluginBundle {
    website = "http://www.develappers.de/"
    vcsUrl = "https://github.com/DevelappersGmbH/android-versioning"

    description = "Provides tasks for bumping version number of an android project."
    tags = listOf("version", "android")

    (plugins) {
        "versioningPlugin" {
            id = "de.develappers.versioning"
            displayName = "Versioning plugin for android projects"
        }
    }
}
