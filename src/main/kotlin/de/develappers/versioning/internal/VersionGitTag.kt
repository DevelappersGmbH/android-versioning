package de.develappers.versioning.internal

import de.develappers.versioning.internal.extensions.capitalized
import de.develappers.versioning.internal.extensions.execute
import org.gradle.api.Project

class VersionGitTag private constructor(
    val prefix: String,
    val flavor: String?,
    val major: Int,
    val minor: Int,
    val patch: Int,
    val build: Int?
) {
    companion object {
        fun last(project: Project, prefix: String = "android", flavorName: String? = null): VersionGitTag? {
            // Create a list of possible filters.
            // Possible filters are combined by prefix and flavors with different casing
            // because the git match parameter is case-sensitive
            val prefixes = listOf(prefix, prefix.capitalized(), prefix.uppercase())
            val flavorNames = listOfNotNull(flavorName, flavorName?.capitalized(), flavorName?.uppercase())

            val filters = prefixes.flatMap { p ->
                if (flavorNames.isEmpty())
                    listOf("$p/*")
                else
                    flavorNames.map { f -> "$p/$f/*" }
            }

            // Search first matching tag for different filters
            for (filter in filters) {
                val tagName = lastTagName(project, filter) ?: continue
                return fromName(tagName)
            }

            return null
        }

        private fun lastTagName(project: Project, filter: String): String? {
            return try {
                listOf(
                    "git", "describe", "--tags", "--match", filter, "--abbrev=0"
                ).execute(project.rootDir)
            } catch (e: java.lang.Exception) {
                null
            }
        }

        private fun fromName(tagName: String): VersionGitTag? {
            // https://regex101.com/r/mbXEkl/2
            val regex = Regex(
                "(?<prefix>\\w*)/((?<flavor>\\w*)/)?(?<major>\\d+)\\.(?<minor>\\d+)(\\.(?<patch>\\d+))?(-(?<build>\\d+))?"
            )

            val result = regex.matchEntire(tagName) ?: return null

            return VersionGitTag(
                result.groups["prefix"]?.value ?: return null,
                result.groups["flavor"]?.value,
                result.groups["major"]?.value?.toIntOrNull() ?: return null,
                result.groups["minor"]?.value?.toIntOrNull() ?: return null,
                result.groups["patch"]?.value?.toIntOrNull() ?: 0,
                result.groups["build"]?.value?.toIntOrNull(),
            )
        }
    }

    override fun toString(): String {
        return "$prefix / $flavor / $major.$minor.$patch - $build"
    }
}