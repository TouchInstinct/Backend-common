@file:Suppress("unused")
package ru.touchin.exception.handler.spring.security

import org.springframework.context.annotation.Import
import ru.touchin.exception.handler.spring.security.configurations.SecurityExceptionHandlerConfiguration

@Import(value = [SecurityExceptionHandlerConfiguration::class])
annotation class EnableSpringSecurityExceptionHandler
