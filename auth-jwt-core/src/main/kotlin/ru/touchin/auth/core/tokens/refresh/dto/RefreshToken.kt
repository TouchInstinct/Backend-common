package ru.touchin.auth.core.tokens.refresh.dto

import ru.touchin.auth.core.user.dto.User
import java.time.ZonedDateTime

data class RefreshToken(
    val value: String,
    val expiresAt: ZonedDateTime,
    val user: User,
)
