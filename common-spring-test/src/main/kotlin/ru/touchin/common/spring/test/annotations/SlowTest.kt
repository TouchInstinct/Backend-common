@file:Suppress("unused")
package ru.touchin.common.spring.test.annotations

import org.springframework.context.annotation.Profile
import org.springframework.test.context.junit.jupiter.DisabledIf

@Profile(value = ["test", "test-slow"])
@Target(allowedTargets = [AnnotationTarget.FUNCTION, AnnotationTarget.CLASS])
@DisabledIf(
    expression = """
       #{systemProperties['tests.slow.enabled'] != null
        ? systemProperties['tests.slow.enabled'].toLowerCase().contains('false')
        : false} 
    """
)
annotation class SlowTest
