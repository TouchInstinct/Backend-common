package ru.touchin.exception.handler.spring.configurations

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import ru.touchin.exception.handler.spring.creators.DefaultExceptionResponseBodyCreatorImpl
import ru.touchin.exception.handler.spring.creators.ExceptionResponseBodyCreator
import ru.touchin.exception.handler.spring.logger.FallbackLogger
import ru.touchin.exception.handler.spring.logger.Logger

@Configuration
@ComponentScan(
    "ru.touchin.exception.handler.spring.advices",
    "ru.touchin.exception.handler.spring.resolvers",
)
class ExceptionHandlerConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun exceptionResponseBodyCreator(): ExceptionResponseBodyCreator {
        return DefaultExceptionResponseBodyCreatorImpl()
    }

    @Bean
    @ConditionalOnMissingBean
    fun logger(): Logger {
        return FallbackLogger()
    }

}
