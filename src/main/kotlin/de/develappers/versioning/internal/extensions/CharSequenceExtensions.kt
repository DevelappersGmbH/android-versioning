package de.develappers.versioning.internal.extensions

import java.util.*

fun CharSequence.capitalized(): String = getOrNull(0)?.let { initial ->
    if (initial.isLowerCase())
        initial.titlecase(Locale.getDefault()) + substring(1)
    else
        toString()
} ?: ""