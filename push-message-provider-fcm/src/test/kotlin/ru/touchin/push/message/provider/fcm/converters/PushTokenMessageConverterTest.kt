package ru.touchin.push.message.provider.fcm.converters

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.AndroidNotification
import com.google.firebase.messaging.ApnsConfig
import com.google.firebase.messaging.Aps
import com.google.firebase.messaging.Message
import org.junit.Assert
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.touchin.push.message.provider.dto.PushMessageNotification
import ru.touchin.push.message.provider.dto.request.PushTokenMessage

@SpringBootTest
class PushTokenMessageConverterTest {

    @Autowired
    lateinit var pushTokenMessageConverter: PushTokenMessageConverter

    @Autowired
    lateinit var pushMessageNotificationConverter: PushMessageNotificationConverter

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("Конвертация сообщения с уведомлением происходит корректно")
    fun invoke_withNotification() {
        val pushMessageNotification = PushMessageNotification(
            title = "title",
            description = "description",
            imageUrl = "imageUrl"
        )
        val pushTokenMessage = PushTokenMessage(
            token = "token",
            pushMessageNotification = pushMessageNotification,
            data = mapOf("testKey" to "testvalue")
        )

        val realResult = pushTokenMessageConverter(pushTokenMessage)
        val realResultJson = objectMapper.writeValueAsString(realResult)

        val expectedResult = Message.builder()
            .setToken(pushTokenMessage.token)
            .setNotification(pushMessageNotificationConverter(pushMessageNotification))
            .putAllData(pushTokenMessage.data)
            .setupApns()
            .setupAndroid()
            .build()

        val expectedResultJson = objectMapper.writeValueAsString(expectedResult)

        Assert.assertEquals(
            "Конвертация некорректна",
            realResultJson,
            expectedResultJson
        )
    }

    @Test
    @DisplayName("Конвертация сообщения без уведомления происходит корректно")
    fun invoke_withoutNotification() {
        val pushTokenMessage = PushTokenMessage(
            token = "token",
            pushMessageNotification = null,
            data = mapOf("testKey" to "testvalue")
        )

        val realResult = pushTokenMessageConverter(pushTokenMessage)
        val realResultJson = objectMapper.writeValueAsString(realResult)

        val expectedResult = Message.builder()
            .setToken(pushTokenMessage.token)
            .putAllData(pushTokenMessage.data)
            .setupApns()
            .setupAndroid()
            .build()

        val expectedResultJson = objectMapper.writeValueAsString(expectedResult)

        Assert.assertEquals(
            "Конвертация некорректна",
            realResultJson,
            expectedResultJson
        )
    }

    private fun Message.Builder.setupApns(): Message.Builder {
        return setApnsConfig(
            ApnsConfig.builder()
                .setAps(
                    Aps.builder()
                        .setSound("default")
                        .setContentAvailable(true)
                        .build()
                )
                .build()
        )
    }

    private fun Message.Builder.setupAndroid(): Message.Builder {
        return setAndroidConfig(
            AndroidConfig.builder()
                .setNotification(
                    AndroidNotification.builder()
                        .setSound("default")
                        .build()
                )
                .build()
        )
    }

}
