package ru.touchin.logger.spring.annotations

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
annotation class LogValue(
    val prefix: String = "",
    val expand: Boolean = false,
)
