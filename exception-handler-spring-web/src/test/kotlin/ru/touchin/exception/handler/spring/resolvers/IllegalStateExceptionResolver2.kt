package ru.touchin.exception.handler.spring.resolvers

import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import ru.touchin.common.spring.web.dto.DefaultApiError
import ru.touchin.exception.handler.dto.ExceptionResolverResult
import java.lang.IllegalStateException

@Component
@Order(2)
class IllegalStateExceptionResolver2: ExceptionResolver {

    override fun invoke(exception: Exception): ExceptionResolverResult? {
        return (exception as? IllegalStateException)?.let {
            ExceptionResolverResult(
                apiError = DefaultApiError(errorCode = -2, errorMessage = "Should not be executed"),
                status = HttpStatus.BAD_GATEWAY,
                exception = exception,
            )
        }
    }

}
