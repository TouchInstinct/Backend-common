@file:Suppress("unused")
package ru.touchin.logger.spring.serializers.resolvers

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import ru.touchin.common.spring.Ordered
import ru.touchin.logger.spring.serializers.resolvers.dto.ResolvedValue
import java.time.temporal.Temporal

@Order(Ordered.NORMAL)
@Component
class DateLogValueResolverImpl : LogValueResolver<String> {

    override fun invoke(value: Any): ResolvedValue<String>? {
        if (value !is Temporal) {
            return null
        }

        return ResolvedValue(
            value = value.toString(),
            typeName = "date",
        )
    }

}
