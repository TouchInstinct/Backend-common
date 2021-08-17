package ru.touchin.auth.core

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import ru.touchin.auth.core.configurations.AuthCoreConfiguration

@SpringBootConfiguration
@ContextConfiguration(classes = [AuthCoreConfiguration::class])
@TestConfiguration
@Import(AuthCoreConfiguration::class, AuthCoreSlowTestConfiguration::class)
class AuthCoreTestApplication
