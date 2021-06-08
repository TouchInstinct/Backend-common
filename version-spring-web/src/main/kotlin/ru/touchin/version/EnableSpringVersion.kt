@file:Suppress("unused")
package ru.touchin.version

import org.springframework.context.annotation.Import
import ru.touchin.version.configurations.SpringVersionConfiguration

@Import(value = [SpringVersionConfiguration::class])
annotation class EnableSpringVersion
