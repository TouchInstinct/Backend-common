package ru.touchin.auth.core.tokens.refresh.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConstructorBinding
@ConfigurationProperties(prefix = "token.refresh")
data class RefreshTokenProperties(
    val length: Int,
    val prefix: String,
    val timeToLive: Duration
)
