package ru.touchin.exception.handler.spring.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "exception.resolver")
data class ExceptionResolverProperties(
    val includeHeaders: Boolean = false,
)
