@file:Suppress("unused")
package ru.touchin.logger.spring.serializers.resolvers

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import ru.touchin.common.spring.Ordered
import ru.touchin.logger.spring.serializers.resolvers.dto.ResolvedValue
import java.net.URI
import java.net.URL

@Order(Ordered.NORMAL)
@Component
class UrlLogValueResolverImpl : LogValueResolver<String> {

    override operator fun invoke(value: Any): ResolvedValue<String>? {
        if (value is URL || value is URI) {
            return ResolvedValue(
                value = value.toString(),
            )
        }

        return null
    }

}
