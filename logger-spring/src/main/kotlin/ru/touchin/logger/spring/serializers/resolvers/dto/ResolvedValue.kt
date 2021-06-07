@file:Suppress("unused")
package ru.touchin.logger.spring.serializers.resolvers.dto

class ResolvedValue<out T: Any>(
    val value: T?,
    val typeName: String = ""
) {

    companion object {
        val SKIP_VALUE = ResolvedValue(value = null)
    }

}
