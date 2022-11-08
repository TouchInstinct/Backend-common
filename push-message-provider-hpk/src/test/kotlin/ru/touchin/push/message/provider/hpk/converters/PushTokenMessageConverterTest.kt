package ru.touchin.push.message.provider.hpk.converters

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.touchin.push.message.provider.dto.PushMessageNotification
import ru.touchin.push.message.provider.dto.request.PushTokenMessage
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.Message
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.Notification as HmsNotification
import org.junit.jupiter.api.Assertions
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.AndroidConfig
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.android.AndroidClickAction
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.android.AndroidNotificationConfig
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android.AndroidClickActionType

@SpringBootTest
class PushTokenMessageConverterTest {

    @Autowired
    lateinit var pushTokenMessageConverter: PushTokenMessageConverter

    @Test
    fun invoke_buildsComplexMessage() {
        val request = PushTokenMessage(
            token = "testToken",
            pushMessageNotification = PushMessageNotification(
                title = "title",
                description = "description",
                imageUrl = "https://avatars.githubusercontent.com/u/1435794?s=200&v=4"
            ),
            data = mapOf(
                "key1" to "value1",
            )
        )

        val actualResult = pushTokenMessageConverter(request)

        val expectedResult = Message.builder()
            .addToken("testToken")
            .setNotification(
               HmsNotification.builder()
                   .setTitle("title")
                   .setBody("description")
                   .setImage("https://avatars.githubusercontent.com/u/1435794?s=200&v=4")
                   .build()
            )
            .setAndroidConfig(
                AndroidConfig.builder()
                    .setAndroidNotificationConfig(
                        AndroidNotificationConfig.builder()
                            .setDefaultSound(true)
                            .build(AndroidClickAction.builder().build(AndroidClickActionType.OPEN_APP))
                    )
                    .build()
            )
            .setData("{\"key1\":\"value1\"}")
            .build()

        Assertions.assertEquals(
            expectedResult,
            actualResult
        )
    }

    @Test
    fun invoke_throwsValidationErrorAtHttpImageUrl() {
        val request = PushTokenMessage(
            token = "testToken",
            pushMessageNotification = PushMessageNotification(
                title = "title",
                description = "description",
                imageUrl = "http://avatars.githubusercontent.com/u/1435794?s=200&v=4"
            ),
            data = mapOf(
                "key1" to "value1",
            )
        )

        Assertions.assertThrows(
            IllegalArgumentException::class.java
        ) { pushTokenMessageConverter(request) }
    }

}
