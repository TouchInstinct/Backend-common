package ru.touchin.auth.core.user.services.dto

import ru.touchin.auth.core.user.dto.enums.IdentifierType
import java.util.*

data class NewUser(
    val deviceId: UUID,
    val identifierType: IdentifierType,
    val username: String,
    val password: String?,
)
