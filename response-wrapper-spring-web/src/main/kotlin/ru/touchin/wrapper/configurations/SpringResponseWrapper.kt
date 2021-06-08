package ru.touchin.wrapper.configurations

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Suppress("SpringFacetCodeInspection")
@Configuration
@ComponentScan("ru.touchin.wrapper.advices", "ru.touchin.wrapper.components")
class SpringResponseWrapper
