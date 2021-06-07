@file:Suppress("unused")
package ru.touchin.logger.spring.serializers.resolvers

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import ru.touchin.common.spring.Ordered
import ru.touchin.logger.spring.serializers.resolvers.dto.ResolvedValue

@Order(Ordered.NORMAL)
@Component
class BooleanLogValueResolverImpl : LogValueResolver<Boolean> {

    override operator fun invoke(value: Any): ResolvedValue<Boolean>? {
        if (value is Boolean) {
            return ResolvedValue(
                value = value,
                typeName = "boolean"
            )
        }

        return null
    }

}
