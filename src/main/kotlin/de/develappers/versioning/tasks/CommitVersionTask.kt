package de.develappers.versioning.tasks

import de.develappers.versioning.helpers.Version
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.concurrent.TimeUnit

open class CommitVersionTask: DefaultTask() {
    @Input
    var flavorName: String? = null

    init {
        group = "Versioning"
        description = "Create a commit for version change"
    }

    @TaskAction
    fun commit() {
        val version = Version(project.projectDir, flavorName)

        // Add version properties
        println("Versioning: Add version.properties to git")
        println(listOf("git", "add", version.file.path).runCommand())


        // Commit changes
        println("Versioning: Commit changes")
        println(listOf("git", "commit", "-m", "Bumped version to $version").runCommand())
    }

    fun List<String>.runCommand(
        workingDir: File = File("."),
        timeoutAmount: Long = 60,
        timeoutUnit: TimeUnit = TimeUnit.SECONDS
    ): String? = try {
        ProcessBuilder(this)
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