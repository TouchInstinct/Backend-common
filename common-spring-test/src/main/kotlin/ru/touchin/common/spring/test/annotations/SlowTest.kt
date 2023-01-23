@file:Suppress("unused")
package ru.touchin.common.spring.test.annotations

import org.springframework.context.annotation.Profile

@Profile(value = ["test", "test-slow"])
@Target(allowedTargets = [AnnotationTarget.FUNCTION, AnnotationTarget.CLASS])
annotation class SlowTest
