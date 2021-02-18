package de.develappers.versioning

import de.develappers.versioning.helpers.Version
import de.develappers.versioning.tasks.BumpTask
import de.develappers.versioning.tasks.CommitVersionTask
import de.develappers.versioning.tasks.ExportVersionTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class Versioning : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            extensions.create("versioning", VersioningExtension::class.java, projectDir)

            afterEvaluate {
                extensions.findByType(com.android.build.gradle.AppExtension::class.java).let { app ->
                    app?.applicationVariants?.whenObjectAdded { variant ->
                        createTasks(this, variant.flavorName)
                    }
                }
            }
        }

    }

    private fun createTasks(project: Project, flavorName: String) {

        val flavorPart = if (flavorName.isNotEmpty()) flavorName.capitalize() else ""
        val flavorName = if (flavorName.isNotEmpty()) flavorName else null

        if (project.tasks.any { it.name == "bump${Version.BumpType.Build}$flavorPart" })
            return

        val bumpTypes =
            arrayOf(Version.BumpType.Build, Version.BumpType.Patch, Version.BumpType.Minor, Version.BumpType.Major)

        bumpTypes.forEach { bumpType ->

            project.tasks.create("bump$bumpType$flavorPart", BumpTask::class.java) {
                it.bumpType = bumpType
                it.flavorName = flavorName
            }

        }

        project.tasks.create("exportVersion$flavorPart", ExportVersionTask::class.java) {
            it.flavorName = flavorName
        }

        project.tasks.create("commitVersion$flavorPart", CommitVersionTask::class.java) {
            it.flavorName = flavorName
        }
    }
}