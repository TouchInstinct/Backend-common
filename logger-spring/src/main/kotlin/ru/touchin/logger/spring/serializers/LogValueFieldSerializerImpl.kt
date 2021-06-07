@file:Suppress("unused")
package ru.touchin.logger.spring.serializers

import org.springframework.stereotype.Component
import ru.touchin.logger.builder.LogDataItem
import ru.touchin.logger.dto.LogValueField
import ru.touchin.logger.spring.serializers.resolvers.LogValueResolver
import ru.touchin.logger.spring.serializers.resolvers.dto.ResolvedValue
import kotlin.reflect.full.declaredMemberProperties

@Component
class LogValueFieldSerializerImpl(
    resolversList: List<LogValueResolver<*>>
) : LogValueFieldSerializer {

    private val resolvers = resolversList.asSequence()

    private fun resolveFieldName(field: LogValueField, resolvedValue: ResolvedValue<*>): String {
        val prefix = field.prefix

        if (prefix != null) {
            return prefix
        }

        var suffix = ""

        if (resolvedValue.typeName.isNotBlank()) {
            suffix = "_${resolvedValue.typeName}"
        }

        return (field.name ?: DEFAULT_RETURN_FIELD_NAME) + suffix
    }

    private fun serialize(field: LogValueField): LogDataItem? {
        val resolvedValue = resolvers
            .mapNotNull { it.invoke(field.value) }
            .firstOrNull()

        if (resolvedValue?.value == null) {
            return null
        }

        return resolveFieldName(field, resolvedValue) to resolvedValue.value
    }

    private fun expand(field: LogValueField): List<LogValueField> {
        if (!field.expand) {
            return listOf(field)
        }

        return field.value::class.declaredMemberProperties
            .mapNotNull { property ->
                property.getter.call(field.value)
                    ?.let { propertyValue ->
                        LogValueField(
                            name = property.name,
                            value = propertyValue,
                            prefix = field.prefix?.let { "$it.${property.name}" }
                        )
                    }
            }
    }

    override operator fun invoke(field: LogValueField): List<LogDataItem> {
        return expand(field).mapNotNull(::serialize)
    }

    companion object {
        private const val DEFAULT_RETURN_FIELD_NAME = "result"
    }

}
