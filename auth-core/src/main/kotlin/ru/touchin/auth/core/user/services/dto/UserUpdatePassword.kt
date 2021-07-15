package ru.touchin.auth.core.user.services.dto

import java.util.UUID

data class UserUpdatePassword(
    val userAccountId: UUID,
    val oldPassword: String?,
    val newPassword: String?
)
