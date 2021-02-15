package de.develappers.versioning.helpers

import java.io.File
import java.util.*

class Version(val projectDir: File, val flavorName: String?) {

    enum class BumpType {
        Build, Patch, Minor, Major
    }

    val isDefaultFlavor: Boolean
        get() = flavorName.isNullOrEmpty()

    val versionName: String
        get() = "$major.$minor.$patch"

    val versionCode: Int
        get() = build

    val file: File by lazy {
        File("${projectDir.absoluteFile}/version.properties")
    }

    private var major: Int = 0
    private var minor: Int = 0
    private var patch: Int = 0
    private var build: Int = 0

    init {
        load()
    }

    fun load() {
        val versionPropertiesFile = file

        if (!versionPropertiesFile.exists()) {
            versionPropertiesFile.createNewFile()
        }

        val versionProperties = Properties()

        versionProperties.load(versionPropertiesFile.inputStream())

        try {
            major = Integer.parseInt(versionProperties.getProperty(key("major")))
            minor = Integer.parseInt(versionProperties.getProperty(key("minor")))
            patch = Integer.parseInt(versionProperties.getProperty(key("patch")))
            build = Integer.parseInt(versionProperties.getProperty(key("build")))
        } catch (e: NumberFormatException) {
            if (isDefaultFlavor)
                println("Versioning: version.properties could not be read -> using default version")
            else
                println("Versioning: version.properties for $flavorName could not be read -> using default version")
        }
    }

    fun save() {
        val versionPropertiesFile = file
        val versionProperties = Properties()

        versionProperties.load(versionPropertiesFile.inputStream())

        versionProperties.setProperty(key("major"), major.toString())
        versionProperties.setProperty(key("minor"), minor.toString())
        versionProperties.setProperty(key("patch"), patch.toString())
        versionProperties.setProperty(key("build"), build.toString())

        versionProperties.store(versionPropertiesFile.writer(), "")
    }

    fun bump(bumpType: BumpType) {
        when (bumpType) {
            BumpType.Build -> bumpBuild()
            BumpType.Patch -> bumpPatch()
            BumpType.Minor -> bumpMinor()
            BumpType.Major -> bumpMajor()
        }

    }

    private fun bumpMajor() {
        major += 1
        minor = 0
        patch = 0
        build += 1
    }

    private fun bumpMinor() {
        minor += 1
        patch = 0
        build += 1
    }

    private fun bumpPatch() {
        patch += 1
        build += 1
    }

    private fun bumpBuild() {
        build += 1
    }

    private fun key(partName: String): String {
        return if (flavorName.isNullOrEmpty())
            "VERSION_${partName.toUpperCase()}"
        else
            "VERSION_${flavorName.toUpperCase()}_${partName.toUpperCase()}"
    }

    override fun toString(): String {
        return "$major.$minor.$patch - $build"
    }
}