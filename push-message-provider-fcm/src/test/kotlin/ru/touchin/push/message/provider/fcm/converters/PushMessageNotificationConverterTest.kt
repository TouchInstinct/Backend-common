package ru.touchin.push.message.provider.fcm.converters

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.touchin.push.message.provider.dto.PushMessageNotification
import com.google.firebase.messaging.Notification as FcmNotification
import org.junit.jupiter.api.DisplayName

@SpringBootTest
class PushMessageNotificationConverterTest {

    @Autowired
    lateinit var pushMessageNotificationConverter: PushMessageNotificationConverter

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("Конвертация уведомления происходит корректно")
    fun invoke_basic() {
        val pushMessageNotification = PushMessageNotification(
            title = "title",
            description = "description",
            imageUrl = "imageUrl"
        )

        val realResult = pushMessageNotificationConverter(pushMessageNotification)
        val realResultJson = objectMapper.writeValueAsString(realResult)

        val expectedResult = FcmNotification.builder()
            .setTitle(pushMessageNotification.title)
            .setBody(pushMessageNotification.description)
            .setImage(pushMessageNotification.imageUrl)
            .build()

        val expectedResultJson = objectMapper.writeValueAsString(expectedResult)

        Assert.assertEquals(
            "Конвертация некорректна",
            realResultJson,
            expectedResultJson
        )
    }

}
