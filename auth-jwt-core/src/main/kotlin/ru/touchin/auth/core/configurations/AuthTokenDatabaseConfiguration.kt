@file:Suppress("unused")

package ru.touchin.auth.core.configurations

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Import

@Import(AuthCoreDatabaseConfiguration::class)
@EntityScan("ru.touchin.auth.core.tokens")
class AuthTokenDatabaseConfiguration
