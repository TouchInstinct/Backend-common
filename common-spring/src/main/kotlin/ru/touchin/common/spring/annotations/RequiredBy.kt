package ru.touchin.common.spring.annotations

import org.springframework.context.annotation.Import
import ru.touchin.common.spring.processors.RequiredByBeanDefinitionPostProcessor

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Import(RequiredByBeanDefinitionPostProcessor::class)
annotation class RequiredBy(vararg val value: String)
