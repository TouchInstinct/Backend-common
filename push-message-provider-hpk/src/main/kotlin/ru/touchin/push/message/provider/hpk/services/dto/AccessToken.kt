package ru.touchin.push.message.provider.hpk.services.dto

import java.time.Instant

data class AccessToken(
    val value: String,
    val expiresAt: Instant,
)
