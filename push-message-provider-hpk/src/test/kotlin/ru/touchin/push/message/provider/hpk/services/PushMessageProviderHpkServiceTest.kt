package ru.touchin.push.message.provider.hpk.services

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.only
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import ru.touchin.push.message.provider.dto.request.PushTokenCheck
import ru.touchin.push.message.provider.dto.request.PushTokenMessage
import ru.touchin.push.message.provider.enums.PushTokenStatus
import ru.touchin.push.message.provider.services.PushMessageProviderService

@SpringBootTest
class PushMessageProviderHpkServiceTest {

    @MockBean
    lateinit var hmsHpkClientService: HmsHpkClientService

    @Autowired
    lateinit var pushMessageProviderService: PushMessageProviderService

    @Test
    @DisplayName("Обработка запроса на отправку единичного сообщения происходит корректно")
    fun send_basic() {
        Mockito.`when`(
            hmsHpkClientService.send(any())
        ).then {
            // returns Unit
        }

        val request = PushTokenMessage(
            token = "testTokenWithLongLength",
            pushMessageNotification = null,
            data = emptyMap()
        )

        pushMessageProviderService.send(request)

        verify(hmsHpkClientService, only()).send(any())
    }

    @Test
    @DisplayName("Обработка запроса на валидацию пуш-токена происходит корректно")
    fun check_basic() {
        Mockito.`when`(
            hmsHpkClientService.check(any())
        ).then {
            PushTokenStatus.UNKNOWN
        }

        pushMessageProviderService.check(PushTokenCheck("testTokenWithLongLength"))

        verify(hmsHpkClientService, only()).check(any())
    }

}
