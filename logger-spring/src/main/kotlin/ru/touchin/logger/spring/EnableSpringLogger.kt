@file:Suppress("unused")
package ru.touchin.logger.spring

import org.springframework.context.annotation.Import
import ru.touchin.logger.spring.configurations.SpringLoggerConfiguration

@Import(value = [SpringLoggerConfiguration::class])
annotation class EnableSpringLogger
