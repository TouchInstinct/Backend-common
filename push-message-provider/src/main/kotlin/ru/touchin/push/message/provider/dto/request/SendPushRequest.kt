package ru.touchin.push.message.provider.dto.request

import ru.touchin.push.message.provider.dto.PushMessageNotification

sealed interface SendPushRequest {

    val pushMessageNotification: PushMessageNotification?
    val data: Map<String, String>

}
