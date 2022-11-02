package ru.touchin.push.message.provider.hpk.services

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Service
import ru.touchin.push.message.provider.hpk.properties.HpkProperties
import ru.touchin.push.message.provider.hpk.services.dto.AccessToken
import java.time.Instant

@Service
class HmsOauthAccessTokenCacheServiceImpl(
    @Qualifier("push-message-provider.hpk.webclient-cachemanager")
    private val cacheManager: CacheManager,
    @Qualifier("push-message-provider.hpk.client-objectmapper")
    private val objectMapper: ObjectMapper,
    private val hpkProperties: HpkProperties,
) : HmsOauthAccessTokenCacheService {

    override fun put(accessToken: AccessToken) {
        getCache()?.put(hpkProperties.webServices.clientId, accessToken)
    }

    override fun get(): AccessToken? { // TODO: implement synchronization for all threads
        val cachedValue = getCache()
            ?.get(hpkProperties.webServices.clientId)
            ?.get()
            ?: return null

        val accessToken = safeCast(cachedValue, object : TypeReference<AccessToken>() {})
            ?: return null

        return if (accessToken.isValid()) {
            accessToken
        } else {
            null
        }
    }

    override fun evict() {
        getCache()?.evict(hpkProperties.webServices.clientId)
    }

    private fun <T> safeCast(item: Any, typeReference: TypeReference<T>): T? {
        return try {
            objectMapper.convertValue(item, typeReference)
        } catch (e: Exception) {
            print(e.message)
            null
        }
    }

    private fun AccessToken.isValid(): Boolean {
        val expirationTime = with(hpkProperties.webServices.oauth) {
            Instant.now().plus(http.connectionTimeout + http.readTimeout + http.writeTimeout)
        }

        return expiresAt.isAfter(expirationTime)
    }

    private fun getCache(): Cache? {
        return cacheManager.getCache(HMS_CLIENT_SERVICE_CACHE_KEY)
    }

    companion object {

        const val HMS_CLIENT_SERVICE_CACHE_KEY = "HMS_CLIENT_SERVICE"

    }

}
