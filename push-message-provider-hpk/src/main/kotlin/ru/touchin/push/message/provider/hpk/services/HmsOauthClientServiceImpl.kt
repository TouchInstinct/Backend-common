package ru.touchin.push.message.provider.hpk.services

import org.springframework.stereotype.Service
import ru.touchin.push.message.provider.exceptions.PushMessageProviderException
import ru.touchin.push.message.provider.hpk.clients.hms_oauth.HmsOauthWebClient
import ru.touchin.push.message.provider.hpk.services.dto.AccessToken
import java.time.Instant

@Service
class HmsOauthClientServiceImpl(
    private val hmsOauthAccessTokenCacheService: HmsOauthAccessTokenCacheService,
    private val hmsOauthWebClient: HmsOauthWebClient,
) : HmsOauthClientService {

    override fun getAccessToken(): String {
        val accessToken = hmsOauthAccessTokenCacheService.get()
            ?: retrieveAccessToken().also(hmsOauthAccessTokenCacheService::put)

        return accessToken.value
    }

    private fun retrieveAccessToken(): AccessToken {
        val result = hmsOauthWebClient.token()

        if (result.success == null) {
            throw PushMessageProviderException(result.failure?.errorDescription.orEmpty(), null)
        }

        return with(result.success) {
            AccessToken(
                value = accessToken,
                expiresAt = Instant.now().plusSeconds(expiresIn)
            )
        }
    }

}
