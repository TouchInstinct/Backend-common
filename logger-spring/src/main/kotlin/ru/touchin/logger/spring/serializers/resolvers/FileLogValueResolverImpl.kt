@file:Suppress("unused")
package ru.touchin.logger.spring.serializers.resolvers

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import ru.touchin.common.spring.Ordered
import ru.touchin.logger.spring.serializers.resolvers.dto.ResolvedValue
import java.io.File

@Order(Ordered.NORMAL)
@Component
class FileLogValueResolverImpl : LogValueResolver<String> {

    override operator fun invoke(value: Any): ResolvedValue<String>? {
        if (value !is File) {
            return null
        }

        return ResolvedValue(
            value = value.toString(),
        )
    }

}
