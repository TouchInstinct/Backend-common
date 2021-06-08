@file:Suppress("unused")
package ru.touchin.exception.handler.spring.logger.resolvers

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import ru.touchin.common.spring.Ordered
import ru.touchin.common.spring.web.dto.ApiError
import ru.touchin.exception.handler.dto.ExceptionResolverResult
import ru.touchin.logger.builder.LogBuilder
import ru.touchin.logger.context.DefaultContextFields
import ru.touchin.logger.context.LoggerExecutionContext
import ru.touchin.logger.dto.LogData
import ru.touchin.logger.dto.LogError

@Order(Ordered.HIGH)
@Component
class BaseLogExceptionResolverResultImpl : LogExceptionResolverResult<LogData> {

    override fun invoke(
        exceptionResolverResult: ExceptionResolverResult,
        logBuilder: LogBuilder<LogData>
    ): LogBuilder<LogData> {
        val errorCode = exceptionResolverResult.apiError.errorCode

        return logBuilder
            .setError(exceptionResolverResult.exception)
            .also { builder ->
                val errorTag = if (errorCode == ApiError.FAILURE_CODE) {
                    LogError.ERROR_FATAL_TAG
                } else {
                    LogError.ERROR_BASE_TAG
                }

                builder.addTags(errorTag)
            }
            .also {
                LoggerExecutionContext.current.updateContext { context ->
                    context.plus(DefaultContextFields.appCode.name to errorCode)
                }
            }
    }

}
