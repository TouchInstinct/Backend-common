@file:Suppress("unused")
package ru.touchin.exception.handler.spring

import org.springframework.context.annotation.Import
import ru.touchin.exception.handler.spring.configurations.ExceptionHandlerConfiguration

@Import(value = [ExceptionHandlerConfiguration::class])
annotation class EnableSpringExceptionHandler
