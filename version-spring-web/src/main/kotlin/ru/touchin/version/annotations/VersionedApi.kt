package ru.touchin.version.annotations

@Target(allowedTargets = [AnnotationTarget.CLASS])
annotation class VersionedApi(
    val value: String = ""
)
