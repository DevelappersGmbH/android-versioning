package de.develappers.versioning.internal.extensions

import java.io.File
import java.util.concurrent.TimeUnit

fun List<String>.execute(workingDir: File): String {
    val process = ProcessBuilder(this).directory(workingDir).start()

    if (!process.waitFor(10, TimeUnit.SECONDS)) {
        process.destroy()
        throw IllegalStateException("Execution timeout: $this")
    }

    val output = process.inputStream.bufferedReader().readText().trim()

    if (process.exitValue() != 0) {
        throw IllegalStateException("Execution of $this failed with exit code: ${process.exitValue()}")
    }

    return output
}