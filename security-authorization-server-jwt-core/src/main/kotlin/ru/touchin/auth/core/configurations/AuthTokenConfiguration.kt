@file:Suppress("unused")

package ru.touchin.auth.core.configurations

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import

@Import(AuthCoreConfiguration::class)
@ComponentScan("ru.touchin.auth.core.tokens")
@ConfigurationPropertiesScan("ru.touchin.auth.core.tokens")
class AuthTokenConfiguration
