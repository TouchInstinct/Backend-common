@file:Suppress("unused")
package ru.touchin.exception.handler.spring.logger.resolvers

import ru.touchin.exception.handler.dto.ExceptionResolverResult
import ru.touchin.logger.builder.LogBuilder

interface LogExceptionResolverResult<T> {

    operator fun invoke(exceptionResolverResult: ExceptionResolverResult, logBuilder: LogBuilder<T>): LogBuilder<T>

}
