package ru.touchin.auth.core.user.converters

import ru.touchin.auth.core.device.dto.Device
import ru.touchin.auth.core.scope.converters.ScopeConverter.toDto
import ru.touchin.auth.core.user.dto.User
import ru.touchin.auth.core.user.models.UserEntity

object UserConverter {

    fun UserEntity.toDto(device: Device?): User {
        return User(
            id = id!!,
            device = device,
            scopes = scopes.map { it.toDto() }.toSet()
        )
    }

}
