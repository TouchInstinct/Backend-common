package ru.touchin.auth.core.user.services.dto

import java.util.UUID

data class UserLogout(
    val deviceId: UUID,
    val userId: UUID,
)
