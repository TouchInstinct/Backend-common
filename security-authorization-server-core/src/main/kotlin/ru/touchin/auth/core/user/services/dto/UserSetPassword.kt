package ru.touchin.auth.core.user.services.dto

import java.util.*

data class UserSetPassword(
    val userAccountId: UUID,
    val newPassword: String
)
