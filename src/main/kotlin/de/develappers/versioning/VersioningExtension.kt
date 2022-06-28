package de.develappers.versioning

import de.develappers.versioning.internal.Version
import de.develappers.versioning.internal.VersionGitTag
import org.gradle.api.Project

open class VersioningExtension(
    private val project: Project,
) {
    private val versions: MutableMap<String, Version> = mutableMapOf()

    val versionName: String get() = getVersionName(null)

    val versionCode: Int get() = getVersionCode(null)

    fun getVersionName(flavorName: String?): String {
        return getVersion(flavorName).versionName
    }

    fun getVersionCode(flavorName: String?): Int {
        return getVersion(flavorName).versionCode
    }

    private fun getVersion(flavorName: String?): Version {
        val key = flavorName ?: "no flavor"
        val existing = versions[key]

        if (existing != null)
            return existing

        val tag = VersionGitTag.last(project, "android", flavorName) ?: VersionGitTag.last(project)

        println("Last matching tag is $tag")

        val version = if (tag != null) {
            Version.next(tag)
        } else {
            Version.default
        }

        versions[key] = version

        println("Version is $version")

        return version
    }
}