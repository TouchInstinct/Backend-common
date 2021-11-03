package ru.touchin.exception.handler.spring.resolvers

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.bind.MissingRequestValueException
import ru.touchin.common.spring.Ordered
import ru.touchin.exception.handler.dto.ExceptionResolverResult

@Order(Ordered.LOW)
@Component
class MissingRequestValueExceptionResolver : ExceptionResolver {

    override fun invoke(exception: Exception): ExceptionResolverResult? {
        if (exception is MissingRequestValueException) {
            return ExceptionResolverResult.createBadRequestError(exception)
        }

        return null
    }

}
