package ru.touchin.push.message.provider.fcm.converters

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.messaging.Message
import org.junit.Assert
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.touchin.push.message.provider.dto.Notification
import ru.touchin.push.message.provider.dto.request.PushTokenMessage

@SpringBootTest
class PushTokenMessageConverterTest {

    @Autowired
    lateinit var pushTokenMessageConverter: PushTokenMessageConverter

    @Autowired
    lateinit var notificationConverter: NotificationConverter

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("Конвертация сообщения с уведомлением происходит корректно")
    fun invoke_withNotification() {
        val notification = Notification(
            title = "title",
            description = "description",
            imageUrl = "imageUrl"
        )
        val pushTokenMessage = PushTokenMessage(
            token = "token",
            notification = notification,
            data = mapOf("testKey" to "testvalue")
        )

        val realResult = pushTokenMessageConverter(pushTokenMessage)
        val realResultJson = objectMapper.writeValueAsString(realResult)

        val expectedResult = Message.builder()
            .setToken(pushTokenMessage.token)
            .setNotification(notificationConverter(notification))
            .putAllData(pushTokenMessage.data)
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
            notification = null,
            data = mapOf("testKey" to "testvalue")
        )

        val realResult = pushTokenMessageConverter(pushTokenMessage)
        val realResultJson = objectMapper.writeValueAsString(realResult)

        val expectedResult = Message.builder()
            .setToken(pushTokenMessage.token)
            .putAllData(pushTokenMessage.data)
            .build()

        val expectedResultJson = objectMapper.writeValueAsString(expectedResult)

        Assert.assertEquals(
            "Конвертация некорректна",
            realResultJson,
            expectedResultJson
        )
    }

}
