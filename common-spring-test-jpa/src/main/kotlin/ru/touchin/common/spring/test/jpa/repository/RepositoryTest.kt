@file:Suppress("unused")
package ru.touchin.common.spring.test.jpa.repository

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.DisabledIf
import ru.touchin.common.spring.test.annotations.SlowTest

@ActiveProfiles("test", "test-slow")
@SlowTest
@DataJpaTest
@Import(RepositoryTestConfiguration::class)
@DisabledIf(
    expression = """
       #{systemProperties['tests.slow.enabled'] != null
        ? systemProperties['tests.slow.enabled'].toLowerCase().contains('false')
        : false} 
    """
)
annotation class RepositoryTest
