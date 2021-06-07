@file:Suppress("unused")
package ru.touchin.common.spring.test.jpa.repository

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import ru.touchin.common.spring.test.annotations.SlowTest

@ActiveProfiles("test", "test-slow")
@SlowTest
@DataJpaTest
@Import(RepositoryTestConfiguration::class)
annotation class RepositoryTest
