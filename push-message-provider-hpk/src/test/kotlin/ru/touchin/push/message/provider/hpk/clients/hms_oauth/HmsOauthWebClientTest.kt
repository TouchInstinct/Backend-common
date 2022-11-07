package ru.touchin.push.message.provider.hpk.clients.hms_oauth

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.touchin.push.message.provider.hpk.clients.hms.enums.HmsResponseCode

@SpringBootTest
class HmsOauthWebClientTest {

    @Autowired
    lateinit var hmsOauthWebClient: HmsOauthWebClient

    @Test
    fun token_invalidClientSecretOnInvalidClientSecret() {
        val result = hmsOauthWebClient.token()

        Assertions.assertNotNull(result.failure)

        Assertions.assertEquals(
            result.failure?.error,
            HmsResponseCode.INVALID_CLIENT_SECRET.value
        )
    }

}
