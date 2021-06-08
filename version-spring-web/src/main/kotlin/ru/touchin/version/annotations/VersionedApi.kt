package ru.touchin.version.annotations

@Versioned("/api/v\${api.version}")
@Target(allowedTargets = [AnnotationTarget.CLASS])
annotation class VersionedApi
