package ru.touchin.exception.handler.spring.resolvers

import org.springframework.core.annotation.Order
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component
import ru.touchin.common.exceptions.CommonNotFoundException
import ru.touchin.common.spring.Ordered
import ru.touchin.exception.handler.dto.ExceptionResolverResult

@Order(Ordered.LOW)
@Component
class AccessDeniedExceptionResolver : ExceptionResolver {

    override fun invoke(exception: Exception): ExceptionResolverResult? {
        if (exception is AccessDeniedException) {
            return ExceptionResolverResult.createAccessDeniedError(exception)
        }

        return null
    }

}