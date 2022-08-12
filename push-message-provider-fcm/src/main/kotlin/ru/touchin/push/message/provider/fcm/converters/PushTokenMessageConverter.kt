package ru.touchin.push.message.provider.fcm.converters

import com.google.firebase.messaging.Message
import org.springframework.stereotype.Component
import ru.touchin.push.message.provider.dto.Notification
import ru.touchin.push.message.provider.dto.request.PushTokenMessage

@Component
class PushTokenMessageConverter(
    private val notificationConverter: NotificationConverter
) {

    operator fun invoke(request: PushTokenMessage): Message {
        return Message.builder()
            .setToken(request.token)
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

}
