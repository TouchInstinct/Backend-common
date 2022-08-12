package ru.touchin.push.message.provider.dto.request

import ru.touchin.push.message.provider.dto.Notification

sealed interface SendPushRequest {

    val notification: Notification?
    val data: Map<String, String>

}
