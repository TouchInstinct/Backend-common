package ru.touchin.auth.core.user.services.dto

import java.util.*

data class AddUserScopes(
    val userId: UUID,
    val scopes: List<String>,
)
