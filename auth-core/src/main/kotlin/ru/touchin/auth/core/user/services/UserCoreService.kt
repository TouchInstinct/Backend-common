package ru.touchin.auth.core.user.services

import ru.touchin.auth.core.user.dto.User
import ru.touchin.auth.core.user.dto.enums.IdentifierType
import ru.touchin.auth.core.user.services.dto.NewAnonymousUser
import ru.touchin.auth.core.user.services.dto.NewUser
import ru.touchin.auth.core.user.services.dto.UserLogin

interface UserCoreService {

    fun create(newAnonymousUser: NewAnonymousUser): User
    fun create(newUser: NewUser): User
    fun get(username: String, identifierType: IdentifierType): User
    fun getOrNull(username: String, identifierType: IdentifierType): User?
    fun login(userLogin: UserLogin): User

}