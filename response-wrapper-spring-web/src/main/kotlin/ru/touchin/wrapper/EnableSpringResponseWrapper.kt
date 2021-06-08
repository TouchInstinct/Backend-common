@file:Suppress("unused")
package ru.touchin.wrapper

import org.springframework.context.annotation.Import
import ru.touchin.wrapper.configurations.SpringResponseWrapper

@Import(value = [SpringResponseWrapper::class])
annotation class EnableSpringResponseWrapper
