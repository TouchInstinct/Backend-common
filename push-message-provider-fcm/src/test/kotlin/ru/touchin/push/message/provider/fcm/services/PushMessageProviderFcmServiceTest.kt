package ru.touchin.push.message.provider.fcm.services

import org.junit.Assert
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import ru.touchin.push.message.provider.dto.request.PushTokenCheck
import ru.touchin.push.message.provider.dto.request.PushTokenMessage
import ru.touchin.push.message.provider.dto.result.CheckPushTokenResult
import ru.touchin.push.message.provider.dto.result.SendPushTokenMessageResult
import ru.touchin.push.message.provider.enums.PushTokenStatus
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

    @Test
    @DisplayName("Обработка запроса на валидацию пуш-токена происходит корректно")
    fun isValid_basic() {
        val expectedClientResult = PushTokenStatus.VALID

        Mockito.`when`(
            fcmClient.check(PushTokenCheck("testToken"))
        ).thenReturn(
            expectedClientResult
        )

        val expectedResult = CheckPushTokenResult(expectedClientResult)
        val realResult = pushMessageProviderService.check(PushTokenCheck("testToken"))

        Assert.assertEquals(
            "Обработка запроса на валидацию пуш-токена происходит корректно",
            expectedResult,
            realResult
        )
    }

}
