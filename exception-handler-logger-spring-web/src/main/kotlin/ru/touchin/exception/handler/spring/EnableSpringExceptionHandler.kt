@file:Suppress("unused")
package ru.touchin.exception.handler.spring

import org.springframework.context.annotation.Import
import ru.touchin.exception.handler.spring.configurations.ExceptionHandlerLoggerConfiguration

@Import(value = [ExceptionHandlerLoggerConfiguration::class])
annotation class EnableSpringExceptionHandlerLogger
