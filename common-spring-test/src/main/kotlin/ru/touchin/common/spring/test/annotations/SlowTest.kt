@file:Suppress("unused")
package ru.touchin.common.spring.test.annotations

import org.springframework.context.annotation.Profile
import org.springframework.test.context.junit.jupiter.DisabledIf

@Profile(value = ["test", "test-slow"])
@DisabledIf("\${tests.slow.disabled:false}")
@Target(allowedTargets = [AnnotationTarget.FUNCTION, AnnotationTarget.CLASS])
annotation class SlowTest
