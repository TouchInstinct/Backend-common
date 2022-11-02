package ru.touchin.push.message.provider.hpk.services

import com.fasterxml.jackson.databind.JsonMappingException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import ru.touchin.push.message.provider.exceptions.InvalidPushTokenException
import ru.touchin.push.message.provider.exceptions.PushMessageProviderException
import ru.touchin.push.message.provider.hpk.base.clients.dto.ConditionalResponse
import ru.touchin.push.message.provider.hpk.clients.hms.enums.HmsResponseCode
import ru.touchin.push.message.provider.hpk.clients.hms_oauth.HmsOauthWebClient
import ru.touchin.push.message.provider.hpk.clients.hms_oauth.response.HmsOauthErrorResponse
import ru.touchin.push.message.provider.hpk.clients.hms_oauth.response.HmsOauthTokenResponse
import java.net.SocketTimeoutException

@SpringBootTest
class HmsOauthClientServiceTest {

    @MockBean
    lateinit var hmsOauthWebClient: HmsOauthWebClient

    @Autowired
    lateinit var hmsOauthClientService: HmsOauthClientService

    @Test
    fun getAccessToken_throwsPushMessageProviderExceptionForUnknownError() {
        Mockito.`when`(
            hmsOauthWebClient.token()
        ).then {
            ConditionalResponse(
                success = null,
                failure = HmsOauthErrorResponse(
                    error = HmsResponseCode.UNKNOWN.value,
                    subError = 0,
                    errorDescription = "errorDescription"
                )
            )
        }

        Assertions.assertThrows(
            PushMessageProviderException::class.java
        ) { hmsOauthClientService.getAccessToken() }
    }

    @Test
    fun getAccessToken_throwsNetworkExceptions() {
        Mockito.`when`(
            hmsOauthWebClient.token()
        ).then {
            throw SocketTimeoutException()
        }

        Assertions.assertThrows(
            SocketTimeoutException::class.java
        ) { hmsOauthClientService.getAccessToken() }
    }


    @Test
    fun getAccessToken_throwsParsingExceptions() {
        Mockito.`when`(
            hmsOauthWebClient.token()
        ).then {
            throw JsonMappingException({ }, "jsonMappingExceptionMsg")
        }

        Assertions.assertThrows(
            JsonMappingException::class.java
        ) { hmsOauthClientService.getAccessToken() }
    }

    @Test
    fun getAccessToken_cachesExpectedTime() {
        Mockito.`when`(
            hmsOauthWebClient.token()
        ).then {
            ConditionalResponse(
                success = HmsOauthTokenResponse(
                    tokenType = "tokenType",
                    expiresIn = 60_000,
                    accessToken = "accessToken"
                ),
                failure = null
            )
        }

        Assertions.assertThrows(
            InvalidPushTokenException::class.java
        ) { hmsOauthClientService.getAccessToken() }
    }


}
