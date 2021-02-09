package de.develappers.versioning.tasks

import de.develappers.versioning.helpers.Version
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.concurrent.TimeUnit

open class CommitVersionTask: DefaultTask() {

    var flavorName: String? = null

    init {
        group = "Versioning"
        description = "Create a commit for version change"
    }

    @TaskAction
    fun commit() {
        val version = Version(flavorName)

        // Add version properties
        println("Versioning: Add version.properties to git")
        "git add ${version.file.path}".runCommand()

        // Commit changes
        println("Versioning: Commit changes")
        "git commit -m \"Bumped version to $version\"".runCommand()
    }

    fun String.runCommand(
        workingDir: File = File("."),
        timeoutAmount: Long = 60,
        timeoutUnit: TimeUnit = TimeUnit.SECONDS
    ): String? = try {
        ProcessBuilder(split("\\s".toRegex()))
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start().apply { waitFor(timeoutAmount, timeoutUnit) }
            .inputStream.bufferedReader().readText()
    } catch (e: java.io.IOException) {
        e.printStackTrace()
        null
    }

}