package ru.touchin.auth.core.tokens.access.services

import ru.touchin.auth.core.tokens.access.dto.AccessToken
import ru.touchin.auth.core.tokens.access.dto.AccessTokenRequest

interface AccessTokenService {

    fun create(accessTokenRequest: AccessTokenRequest): AccessToken

}
