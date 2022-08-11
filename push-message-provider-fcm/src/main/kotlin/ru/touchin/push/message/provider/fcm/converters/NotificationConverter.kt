package ru.touchin.push.message.provider.fcm.converters

import com.google.firebase.messaging.Notification as FcmNotification
import org.springframework.stereotype.Component
import ru.touchin.push.message.provider.dto.Notification

@Component
class NotificationConverter {

    operator fun invoke(notification: Notification): FcmNotification {
        return FcmNotification.builder()
            .setTitle(notification.title)
            .setBody(notification.description)
            .setImage(notification.imageUrl)
            .build()
    }

}
