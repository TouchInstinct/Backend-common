package ru.touchin.auth.core.tokens.refresh.services

import ru.touchin.auth.core.tokens.refresh.dto.RefreshToken
import ru.touchin.auth.core.tokens.refresh.services.dto.NewRefreshToken

interface RefreshTokenCoreService {

    fun get(value: String): RefreshToken
    fun create(token: NewRefreshToken): RefreshToken

}
