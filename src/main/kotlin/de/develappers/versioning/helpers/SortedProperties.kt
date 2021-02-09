package de.develappers.versioning.helpers

import java.util.*

class SortedProperties: Properties() {
    override fun keys(): Enumeration<Any> {
        val keys = super.keys().toList()
            .sortedWith(compareBy { it as String })

        return Vector(keys).elements()
    }
}