package ru.touchin.logger.spring.annotations

@Target(AnnotationTarget.FUNCTION)
annotation class AutoLogging(
    val tags: Array<String>,
    val preventError: Boolean = false,
)
