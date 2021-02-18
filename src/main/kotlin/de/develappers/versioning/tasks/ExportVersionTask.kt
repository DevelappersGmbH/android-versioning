package de.develappers.versioning.tasks

import de.develappers.versioning.helpers.Version
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ExportVersionTask : DefaultTask() {

    var flavorName: String? = null

    init {
        group = "Versioning"
        description = "Exports version name to file 'version.txt'"
    }

    @TaskAction
    fun exportVersion() {
        val version = Version(project.projectDir, flavorName)
        val versionName = "${version.versionName}-${version.versionCode}"

        println("Versioning: Export version name $versionName to 'version.txt'")

        val versionFile = File("version.txt")

        if (!versionFile.exists())
            versionFile.createNewFile()

        versionFile.writeText(versionName)
    }
}