package ru.touchin.auth.core.user.services.dto

import ru.touchin.auth.core.user.dto.enums.IdentifierType
import java.util.UUID

data class GetUserAccount(
    val userId: UUID,
    val identifierType: IdentifierType,
)
