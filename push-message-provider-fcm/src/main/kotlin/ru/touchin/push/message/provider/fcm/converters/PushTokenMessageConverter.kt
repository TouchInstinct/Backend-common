package ru.touchin.push.message.provider.fcm.converters

import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.AndroidNotification
import com.google.firebase.messaging.ApnsConfig
import com.google.firebase.messaging.Aps
import com.google.firebase.messaging.Message
import org.springframework.stereotype.Component
import ru.touchin.push.message.provider.dto.Notification
import ru.touchin.push.message.provider.dto.request.PushTokenMessage

@Component
class PushTokenMessageConverter(
    private val notificationConverter: NotificationConverter
) {

    private companion object {

        const val PLATFORMS_KEY_SOUND_ON = "default"

    }

    operator fun invoke(request: PushTokenMessage): Message {
        return Message.builder()
            .setToken(request.token)
            .setupApns()
            .setupAndroid()
            .setIfExists(request.notification)
            .putAllData(request.data)
            .build()
    }

    private fun Message.Builder.setIfExists(notification: Notification?): Message.Builder {
        return if (notification != null) {
            setNotification(notificationConverter(notification))
        } else {
            this
        }
    }

    private fun Message.Builder.setupApns(): Message.Builder {
        return setApnsConfig(
            ApnsConfig.builder()
                .setAps(
                    Aps.builder()
                        .setSound(PLATFORMS_KEY_SOUND_ON)
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
                        .setSound(PLATFORMS_KEY_SOUND_ON)
                        .build()
                )
                .build()
        )
    }

}
