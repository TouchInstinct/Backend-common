@file:Suppress("unused")
package ru.touchin.logger.spring.serializers.resolvers

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import ru.touchin.common.spring.Ordered
import ru.touchin.logger.spring.serializers.resolvers.dto.ResolvedValue

@Order(Ordered.NORMAL)
@Component
class NumberLogValueResolverImpl : LogValueResolver<Number> {

    override operator fun invoke(value: Any): ResolvedValue<Number>? {
        if (value is Number) {
            return ResolvedValue(
                value = value,
                typeName = "number"
            )
        }

        return null
    }

}
