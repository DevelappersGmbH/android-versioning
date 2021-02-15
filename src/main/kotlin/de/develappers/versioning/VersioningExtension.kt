package de.develappers.versioning

import de.develappers.versioning.helpers.Version
import java.io.File

open class VersioningExtension(val projectDir: File) {
    val versionName: String
        get() = getVersionName(null)

    val versionCode: Int
        get() = getVersionCode(null)

    fun getVersionName(flavorName: String?): String {
        return Version(projectDir, flavorName).versionName
    }

    fun getVersionCode(flavorName: String?): Int {
        return Version(projectDir, flavorName).versionCode
    }

}