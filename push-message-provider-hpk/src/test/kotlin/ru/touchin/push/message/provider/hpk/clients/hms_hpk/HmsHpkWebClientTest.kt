package ru.touchin.push.message.provider.hpk.clients.hms_hpk

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.touchin.push.message.provider.hpk.clients.hms.enums.HmsResponseCode
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.bodies.HmsHpkMessagesSendBody
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.AndroidConfig
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.Message
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.Notification
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.android.AndroidClickAction
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.android.AndroidNotificationConfig
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android.AndroidClickActionType
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android.AndroidUrgency
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.requests.HmsHpkMessagesSendRequest

@SpringBootTest
class HmsHpkWebClientTest {

    @Autowired
    lateinit var hmsHpkWebClient: HmsHpkWebClient

    @Test
    fun messagesSend_pushTokenNotSpecified() {
        val result = hmsHpkWebClient.messagesSend(
            HmsHpkMessagesSendRequest(
                hmsHpkMessagesSendBody = HmsHpkMessagesSendBody(
                    validateOnly = true,
                    message = Message.builder()
                        .addToken("pushTokenWithLongLength")
                        .setNotification(
                            Notification.builder()
                                .setTitle("title")
                                .setBody("body")
                                .setImage("https://avatars.githubusercontent.com/u/1435794?s=200&v=4")
                                .build()
                        )
                        .setAndroidConfig(
                            AndroidConfig.builder()
                                .setUrgency(AndroidUrgency.HIGH)
                                .setAndroidNotificationConfig(
                                    AndroidNotificationConfig.builder()
                                        .setDefaultSound(true)
                                        .build(AndroidClickAction.builder().build(AndroidClickActionType.OPEN_APP))
                                )
                                .build()
                        )
                        .build()
                ),
                accessToken = "testAccessToken"
            )
        )

        Assertions.assertEquals(
            HmsResponseCode.PERMISSION_DENIED.value.toString(),
            result.code
        )
    }

}
