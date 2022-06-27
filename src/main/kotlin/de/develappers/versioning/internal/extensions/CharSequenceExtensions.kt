package de.develappers.versioning.internal.extensions

import java.util.*

fun CharSequence.capitalized(): String =
    when {
        isEmpty() -> ""
        else -> get(0).let { initial ->
            when {
                initial.isLowerCase() -> initial.titlecase(Locale.getDefault()) + substring(1)
                else -> toString()
            }
        }
    }