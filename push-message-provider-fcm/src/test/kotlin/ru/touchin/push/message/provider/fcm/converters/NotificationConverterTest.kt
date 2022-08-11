package ru.touchin.push.message.provider.fcm.converters

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.touchin.push.message.provider.dto.Notification
import com.google.firebase.messaging.Notification as FcmNotification
import org.junit.jupiter.api.DisplayName

@SpringBootTest
class NotificationConverterTest {

    @Autowired
    lateinit var notificationConverter: NotificationConverter

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("Конвертация уведомления происходит корректно")
    fun invoke_basic() {
        val notification = Notification(
            title = "title",
            description = "description",
            imageUrl = "imageUrl"
        )

        val realResult = notificationConverter(notification)
        val realResultJson = objectMapper.writeValueAsString(realResult)

        val expectedResult = FcmNotification.builder()
            .setTitle(notification.title)
            .setBody(notification.description)
            .setImage(notification.imageUrl)
            .build()

        val expectedResultJson = objectMapper.writeValueAsString(expectedResult)

        Assert.assertEquals(
            "Конвертация некорректна",
            realResultJson,
            expectedResultJson
        )
    }

}
