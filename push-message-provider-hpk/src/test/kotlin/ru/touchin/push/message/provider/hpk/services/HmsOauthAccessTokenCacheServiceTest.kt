package ru.touchin.push.message.provider.hpk.services

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.touchin.push.message.provider.hpk.services.dto.AccessToken
import java.time.Instant

@SpringBootTest
class HmsOauthAccessTokenCacheServiceTest {

    @Autowired
    lateinit var hmsOauthAccessTokenCacheService: HmsOauthAccessTokenCacheService

    private val validAccessToken: AccessToken
        get() = AccessToken(
            value = "token",
            expiresAt = Instant.now().plusSeconds(600)
        )

    private val expiredAccessToken: AccessToken
        get() = AccessToken(
            value = "token",
            expiresAt = Instant.now().minusSeconds(600)
        )

    @Test
    fun get_noCacheReturnsNull() {
        hmsOauthAccessTokenCacheService.evict()

        val accessToken = hmsOauthAccessTokenCacheService.get()

        Assertions.assertNull(accessToken)
    }

    @Test
    fun get_validIsReturned() {
        val expected = validAccessToken

        hmsOauthAccessTokenCacheService.put(expected)

        val actual = hmsOauthAccessTokenCacheService.get()

        Assertions.assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun get_expiredIsNotReturned() {
        hmsOauthAccessTokenCacheService.put(expiredAccessToken)

        val accessToken = hmsOauthAccessTokenCacheService.get()

        Assertions.assertNull(accessToken)
    }

    @Test
    fun put_valid() {
        hmsOauthAccessTokenCacheService.put(validAccessToken)

        val result = hmsOauthAccessTokenCacheService.get()

        Assertions.assertNotNull(result)
    }

    @Test
    fun put_expired() {
        hmsOauthAccessTokenCacheService.put(expiredAccessToken)

        val result = hmsOauthAccessTokenCacheService.get()

        Assertions.assertNull(result)
    }

    @Test
    fun evict_deletesCache() {
        hmsOauthAccessTokenCacheService.put(validAccessToken)
        hmsOauthAccessTokenCacheService.evict()

        val result = hmsOauthAccessTokenCacheService.get()

        Assertions.assertNull(result)
    }

}
