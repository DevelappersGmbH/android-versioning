package de.develappers.versioning

import de.develappers.versioning.helpers.Version

open class VersioningExtension {

    val versionName: String
        get() = getVersionName(null)

    val versionCode: Int
        get() = getVersionCode(null)

    fun getVersionName(flavorName: String?): String {
        return Version(flavorName).versionName
    }

    fun getVersionCode(flavorName: String?): Int {
        return Version(flavorName).versionCode
    }

}