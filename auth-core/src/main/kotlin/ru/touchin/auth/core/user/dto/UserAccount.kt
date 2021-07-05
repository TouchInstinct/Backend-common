package ru.touchin.auth.core.user.dto

import ru.touchin.auth.core.user.dto.enums.IdentifierType

data class UserAccount(
    val username: String,
    val identifierType: IdentifierType
)
