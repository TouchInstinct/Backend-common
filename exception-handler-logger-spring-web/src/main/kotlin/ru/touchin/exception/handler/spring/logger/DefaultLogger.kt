package ru.touchin.exception.handler.spring.logger

import ru.touchin.exception.handler.dto.ExceptionResolverResult
import ru.touchin.exception.handler.spring.logger.resolvers.LogExceptionResolverResult
import ru.touchin.logger.dto.LogData
import ru.touchin.logger.factory.LogBuilderFactory

class DefaultLogger(
    private val logExceptionResolverResults: List<LogExceptionResolverResult<LogData>>,
    private val logBuilderFactory: LogBuilderFactory<LogData>,
    ) : Logger {

    override fun log(clazz: Class<*>, exceptionResolverResult: ExceptionResolverResult) {
        logBuilderFactory.create(this::class.java)
            .let { initialBuilder ->
                logExceptionResolverResults.fold(initialBuilder) { builder, resolver ->
                    resolver(exceptionResolverResult, builder)
                }
            }
            .also { builder ->
                builder.build().log()
            }
    }

}
