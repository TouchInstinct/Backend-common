package ru.touchin.logger.spring.configurations

import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.Scope
import ru.touchin.logger.spring.aspects.LogAspect
import ru.touchin.logger.factory.LogBuilderFactory
import ru.touchin.logger.creator.LogCreator
import ru.touchin.logger.creator.JsonLogCreatorImpl
import ru.touchin.logger.factory.LogBuilderFactoryImpl
import ru.touchin.logger.creator.SimpleLogCreatorImpl
import ru.touchin.logger.dto.LogData
import ru.touchin.logger.spring.serializers.LogValueFieldSerializer

@Configuration
@ComponentScan("ru.touchin.logger.spring.serializers", "ru.touchin.logger.spring.listeners")
class SpringLoggerConfiguration {

    @Bean
    @Primary
    @Profile("json-log")
    fun jsonLogCreator(): LogCreator<LogData> {
        return JsonLogCreatorImpl()
    }

    @Bean
    fun localLogCreator(): LogCreator<LogData> {
        return SimpleLogCreatorImpl()
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    fun logBuilderFactory(logCreator: LogCreator<LogData>): LogBuilderFactory<LogData> {
        return LogBuilderFactoryImpl(logCreator)
    }

    @Bean
    fun logAspect(
        logBuilderFactory: LogBuilderFactory<LogData>,
        logValueFieldSerializer: LogValueFieldSerializer
    ): LogAspect {
        return LogAspect(logBuilderFactory, logValueFieldSerializer)
    }

}
