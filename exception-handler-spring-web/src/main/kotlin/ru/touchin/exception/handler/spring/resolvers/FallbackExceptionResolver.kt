package ru.touchin.exception.handler.spring.resolvers

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import ru.touchin.exception.handler.dto.ExceptionResolverResult

@Order(Ordered.LOWEST_PRECEDENCE)
@Component
class FallbackExceptionResolver : ExceptionResolver {

    override fun invoke(exception: Exception): ExceptionResolverResult {
        return ExceptionResolverResult.createInternalError(exception)
    }

}
