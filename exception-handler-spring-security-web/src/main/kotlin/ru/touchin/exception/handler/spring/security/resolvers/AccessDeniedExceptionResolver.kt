package ru.touchin.exception.handler.spring.security.resolvers

import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component
import ru.touchin.common.spring.Ordered
import ru.touchin.common.spring.web.dto.DefaultApiError
import ru.touchin.exception.handler.dto.ExceptionResolverResult
import ru.touchin.exception.handler.spring.resolvers.ExceptionResolver

@Order(Ordered.LOW)
@Component
class AccessDeniedExceptionResolver : ExceptionResolver {

    override fun invoke(exception: Exception): ExceptionResolverResult? {
        if (exception is AccessDeniedException) {
            return createAccessDeniedError(exception)
        }

        return null
    }

    private fun createAccessDeniedError(exception: Exception?): ExceptionResolverResult {
        return ExceptionResolverResult(
            apiError = DefaultApiError.createFailure(exception?.message),
            status = HttpStatus.FORBIDDEN,
            exception = exception,
        )
    }

}
