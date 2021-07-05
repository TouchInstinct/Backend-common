package ru.touchin.auth.core.user.converters

import ru.touchin.auth.core.user.dto.UserAccount
import ru.touchin.auth.core.user.models.UserAccountEntity

object UserAccountConverter {

    fun UserAccountEntity.toDto(): UserAccount {
        return UserAccount(
            username = username,
            identifierType = identifierType
        )
    }

}
