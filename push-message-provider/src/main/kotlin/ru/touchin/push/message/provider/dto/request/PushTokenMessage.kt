package ru.touchin.push.message.provider.dto.request

import ru.touchin.push.message.provider.dto.PushMessageNotification

class PushTokenMessage(
    val token: String,
    override val pushMessageNotification: PushMessageNotification?,
    override val data: Map<String, String>
) : SendPushRequest
