package ru.touchin.push.message.provider.fcm.converters

import com.google.firebase.messaging.Notification as FcmNotification
import org.springframework.stereotype.Component
import ru.touchin.push.message.provider.dto.PushMessageNotification

@Component("push-message-provider.fcm.push-message-notification-converter")
class PushMessageNotificationConverter {

    operator fun invoke(pushMessageNotification: PushMessageNotification): FcmNotification {
        return FcmNotification.builder()
            .setTitle(pushMessageNotification.title)
            .setBody(pushMessageNotification.description)
            .setImage(pushMessageNotification.imageUrl)
            .build()
    }

}
