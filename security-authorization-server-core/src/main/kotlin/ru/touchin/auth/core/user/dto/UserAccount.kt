package ru.touchin.auth.core.user.dto

import ru.touchin.auth.core.user.dto.enums.IdentifierType
import java.util.UUID

data class UserAccount(
    val id: UUID,
    val username: String,
    val identifierType: IdentifierType
)
