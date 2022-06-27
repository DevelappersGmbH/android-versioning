package de.develappers.versioning.internal

class Version(
    val major: Int,
    val minor: Int,
    val patch: Int,
    val build: Int?
) {
    companion object {
        fun next(tag: VersionGitTag): Version {
            return Version(
                tag.major,
                tag.minor,
                tag.patch,
                tag.build
            )
        }

        val default: Version get() = Version(0, 1, 0, 1)
    }

    val versionName: String get() = "$major.$minor.$patch"

    val versionCode: Int get() = if (build != null) {
        build + 1
    } else {
        major * 1_000_000_000 + minor * 1_000_000 + patch * 1_000
    }

    override fun toString(): String {
        return "$versionName - $versionCode"
    }
}