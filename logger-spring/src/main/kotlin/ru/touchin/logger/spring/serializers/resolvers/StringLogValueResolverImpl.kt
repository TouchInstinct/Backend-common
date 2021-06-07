@file:Suppress("unused")
package ru.touchin.logger.spring.serializers.resolvers

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import ru.touchin.common.spring.Ordered
import ru.touchin.logger.spring.serializers.resolvers.dto.ResolvedValue

@Order(Ordered.NORMAL)
@Component
class StringLogValueResolverImpl : LogValueResolver<String> {

    override fun invoke(value: Any): ResolvedValue<String>? {
        if (value is String) {
            return ResolvedValue(
                value = value
            )
        }

        return null
    }
}
