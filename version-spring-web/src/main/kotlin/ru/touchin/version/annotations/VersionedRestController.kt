package ru.touchin.version.annotations

import org.springframework.web.bind.annotation.RestController

@RestController
@VersionedApi("/api/v\${api.version}")
@Target(allowedTargets = [AnnotationTarget.CLASS])
annotation class VersionedRestController
