package ru.touchin.auth.core.tokens.refresh.services.dto

import ru.touchin.auth.core.scope.dto.Scope
import java.util.*

data class NewRefreshToken(
    val userId: UUID,
    val deviceId: UUID?,
    val scopes: Set<Scope>
)
