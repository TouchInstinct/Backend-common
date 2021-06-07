package ru.touchin.logger.dto

class LogValueField(
    val name: String?,
    val value: Any,
    val prefix: String? = null,
    val expand: Boolean = false,
)
