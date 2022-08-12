package ru.touchin.push.message.provider.dto.request

import ru.touchin.push.message.provider.dto.Notification

class PushTokenMessage(
    val token: String,
    override val notification: Notification?,
    override val data: Map<String, String>
) : SendPushRequest
