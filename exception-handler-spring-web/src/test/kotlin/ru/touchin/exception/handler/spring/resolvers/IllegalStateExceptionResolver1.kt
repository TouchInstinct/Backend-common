package ru.touchin.exception.handler.spring.resolvers

import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import ru.touchin.common.spring.web.dto.DefaultApiError
import ru.touchin.exception.handler.dto.ExceptionResolverResult

@Component
@Order(1)
class IllegalStateExceptionResolver1: ExceptionResolver {

    override fun invoke(exception: Exception): ExceptionResolverResult? {
        return (exception as? IllegalStateException)?.let {
            ExceptionResolverResult(
                apiError = DefaultApiError(errorCode = -2, errorMessage = exception.message),
                status = HttpStatus.BAD_REQUEST,
                exception = exception,
            )
        }
    }

}
