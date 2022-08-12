package ru.touchin.push.message.provider.fcm.services

import org.junit.Assert
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import ru.touchin.push.message.provider.dto.request.PushTokenMessage
import ru.touchin.push.message.provider.dto.result.SendPushTokenMessageResult
import ru.touchin.push.message.provider.fcm.clients.FcmClient
import ru.touchin.push.message.provider.services.PushMessageProviderService

@SpringBootTest
class PushMessageProviderFcmServiceTest {

    @MockBean
    lateinit var fcmClient: FcmClient

    @Autowired
    lateinit var pushMessageProviderService: PushMessageProviderService

    @Test
    @DisplayName("Обработка запроса на отправку единичного сообщения происходит корректно")
    fun send_basic() {
        val request = PushTokenMessage(
            token = "testToken",
            notification = null,
            data = emptyMap()
        )

        val expectedResult = SendPushTokenMessageResult("testMessageId")

        Mockito.`when`(
            fcmClient.sendPushTokenMessage(request)
        ).thenReturn(
            expectedResult
        )

        val realResult = pushMessageProviderService.send(request)

        Assert.assertEquals(
            "Обработка запроса на отправку единичного сообщения происходит некорректно",
            expectedResult,
            realResult
        )
    }

}
