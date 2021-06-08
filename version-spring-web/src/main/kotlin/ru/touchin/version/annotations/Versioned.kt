package ru.touchin.version.annotations

@Target(allowedTargets = [AnnotationTarget.CLASS])
internal annotation class Versioned(
    val value: String = ""
)
