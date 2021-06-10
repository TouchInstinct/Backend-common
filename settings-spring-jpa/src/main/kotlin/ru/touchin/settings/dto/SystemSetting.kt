package ru.touchin.settings.dto

data class SystemSetting<T>(
    val key: String,
    val value: T,
)
