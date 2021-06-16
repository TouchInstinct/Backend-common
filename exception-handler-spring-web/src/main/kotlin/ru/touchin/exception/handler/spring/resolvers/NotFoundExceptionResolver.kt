package ru.touchin.exception.handler.spring.resolvers

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import ru.touchin.common.exceptions.CommonNotFoundException
import ru.touchin.exception.handler.dto.ExceptionResolverResult

@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Component
class NotFoundExceptionResolver : ExceptionResolver {

    override fun invoke(exception: Exception): ExceptionResolverResult? {
        return if (exception is CommonNotFoundException) {
            ExceptionResolverResult.createNotFoundError(exception)
        } else {
            null
        }
    }

}
