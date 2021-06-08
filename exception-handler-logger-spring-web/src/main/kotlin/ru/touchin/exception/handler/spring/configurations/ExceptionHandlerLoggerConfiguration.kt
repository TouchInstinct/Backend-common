package ru.touchin.exception.handler.spring.configurations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import ru.touchin.exception.handler.spring.logger.DefaultLogger
import ru.touchin.exception.handler.spring.logger.Logger
import ru.touchin.exception.handler.spring.logger.resolvers.LogExceptionResolverResult
import ru.touchin.logger.dto.LogData
import ru.touchin.logger.factory.LogBuilderFactory
import ru.touchin.logger.spring.EnableSpringLogger

@Configuration
@ComponentScan("ru.touchin.exception.handler.spring.logger.resolvers")
@EnableSpringLogger
class ExceptionHandlerLoggerConfiguration {

    @Bean
    fun logger(
        logExceptionResolverResults: List<LogExceptionResolverResult<LogData>>,
        logBuilderFactory: LogBuilderFactory<LogData>,
    ): Logger {
        return DefaultLogger(logExceptionResolverResults, logBuilderFactory)
    }

}
