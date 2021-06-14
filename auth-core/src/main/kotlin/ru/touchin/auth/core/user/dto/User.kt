package ru.touchin.auth.core.user.dto

import ru.touchin.auth.core.device.dto.Device
import ru.touchin.auth.core.scope.dto.Scope
import java.util.*

data class User(
    val id: UUID,
    val device: Device?,
    val scopes: Set<Scope>,
)
