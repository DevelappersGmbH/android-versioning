package de.develappers.versioning

import org.gradle.api.Plugin
import org.gradle.api.Project

class Versioning : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create("versioning", VersioningExtension::class.java, target)
    }
}