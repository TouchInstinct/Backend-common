package ru.touchin.push.message.provider.fcm.converters

import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.AndroidNotification
import com.google.firebase.messaging.ApnsConfig
import com.google.firebase.messaging.Aps
import com.google.firebase.messaging.Message
import org.springframework.stereotype.Component
import ru.touchin.push.message.provider.dto.PushMessageNotification
import ru.touchin.push.message.provider.dto.request.PushTokenMessage

@Component
class PushTokenMessageConverter(
    private val pushMessageNotificationConverter: PushMessageNotificationConverter
) {

    private companion object {

        const val PLATFORMS_KEY_SOUND_ON = "default"
        const val IOS_ENABLE_BACKGROUND_UPDATE = true

    }

    operator fun invoke(request: PushTokenMessage): Message {
        return Message.builder()
            .setToken(request.token)
            .setupApns()
            .setupAndroid()
            .setIfExists(request.pushMessageNotification)
            .putAllData(request.data)
            .build()
    }

    private fun Message.Builder.setIfExists(pushMessageNotification: PushMessageNotification?): Message.Builder {
        return if (pushMessageNotification != null) {
            setNotification(pushMessageNotificationConverter(pushMessageNotification))
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
                        .setContentAvailable(IOS_ENABLE_BACKGROUND_UPDATE)
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
