package de.develappers.versioning.tasks

import de.develappers.versioning.helpers.Version
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class BumpTask: DefaultTask() {
    @Input
    var bumpType: Version.BumpType = Version.BumpType.Build

    @Input
    var flavorName: String? = null

    init {
        group = "versioning"
    }

    @TaskAction
    fun bump() {
        val version = Version(project.projectDir, flavorName)

        println("Versioning: bump $bumpType of version $version")

        version.bump(bumpType)
        version.save()

        println("Versioning: version bumped to $version")
    }

    @Input
    override fun getDescription(): String {
        return if (flavorName.isNullOrEmpty())
            "Applies a $bumpType bump to version"
        else
            "Applies a $bumpType bump to version for flavor $flavorName"
    }
}