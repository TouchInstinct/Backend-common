@file:Suppress("unused")
package ru.touchin.logger.spring.serializers.resolvers

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import ru.touchin.common.spring.Ordered
import ru.touchin.logger.spring.serializers.resolvers.dto.ResolvedValue

@Order(Ordered.LOW)
@Component
class ObjectLogValueResolverImpl : LogValueResolver<String> {

    private val objectMapper = ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)

    override operator fun invoke(value: Any): ResolvedValue<String> {
        return ResolvedValue(
            value = objectMapper.writeValueAsString(value)
        )
    }

}
