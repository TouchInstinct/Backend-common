package ru.touchin.auth.core.tokens.access.dto

import java.time.Duration

data class AccessToken(
    val value: String,
    val timeToLive: Duration
)
