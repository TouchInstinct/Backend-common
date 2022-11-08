package ru.touchin.push.message.provider.hpk.converters

import org.springframework.stereotype.Component
import ru.touchin.push.message.provider.dto.PushMessageNotification
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.Notification as HmsNotification
import ru.touchin.push.message.provider.hpk.base.extensions.ifNotNull

@Component("push-message-provider.hpk.push-message-notification-converter")
class PushMessageNotificationConverter {

    internal operator fun invoke(pushMessageNotification: PushMessageNotification): HmsNotification {
        return HmsNotification.builder()
            .ifNotNull(pushMessageNotification.imageUrl) { setImage(it) }
            .ifNotNull(pushMessageNotification.title) { setTitle(it) }
            .ifNotNull(pushMessageNotification.description) { setBody(it) }
            .build()
    }

}
