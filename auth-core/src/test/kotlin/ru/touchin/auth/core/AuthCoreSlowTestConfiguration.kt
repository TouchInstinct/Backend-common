package ru.touchin.auth.core

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import ru.touchin.auth.core.configurations.AuthCoreDatabaseConfiguration

@Profile("test-slow")
@EnableAutoConfiguration
@TestConfiguration
@ComponentScan
@Import(AuthCoreDatabaseConfiguration::class)
class AuthCoreSlowTestConfiguration
