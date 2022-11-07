package ru.touchin.push.message.provider.hpk.services

import ru.touchin.push.message.provider.hpk.services.dto.AccessToken

interface HmsOauthAccessTokenCacheService {

    fun put(accessToken: AccessToken)
    fun get(): AccessToken?
    fun evict()

}
