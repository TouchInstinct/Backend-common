package ru.touchin.push.message.provider.hpk.services

import com.nhaarman.mockitokotlin2.any
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import ru.touchin.push.message.provider.dto.PushMessageNotification
import ru.touchin.push.message.provider.dto.request.PushTokenMessage
import ru.touchin.push.message.provider.exceptions.InvalidPushTokenException
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.HmsHpkWebClient
import ru.touchin.push.message.provider.hpk.clients.hms.enums.HmsResponseCode
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.responses.HmsHpkResponse

@SpringBootTest
class HmsHpkClientServiceTest {

    @MockBean
    lateinit var hmsHpkWebClient: HmsHpkWebClient

    @MockBean
    lateinit var hmsOauthClientService: HmsOauthClientService

    @Autowired
    lateinit var hmsHpkClientService: HmsHpkClientService

    @Test
    fun getAccessToken_throwsInvalidPushTokenExceptionForKnownErrors() {
        Mockito.`when`(hmsOauthClientService.getAccessToken()).then { "accessToken" }

        Mockito.`when`(
            hmsHpkWebClient.messagesSend(any())
        ).then {
            HmsHpkResponse(
                code = HmsResponseCode.INVALID_TOKEN.value.toString(),
                msg = "0",
                requestId = "requestId"
            )
        }

        val pushTokenMessage = PushTokenMessage(
            token = "token",
            pushMessageNotification = PushMessageNotification(
                title = "title",
                description = "description",
                imageUrl = null
            ),
            data = emptyMap()
        )

        Assertions.assertThrows(
            InvalidPushTokenException::class.java
        ) { hmsHpkClientService.send(pushTokenMessage) }
    }

}
