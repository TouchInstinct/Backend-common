package ru.touchin.push.message.provider.dto.result

import ru.touchin.push.message.provider.enums.PushTokenStatus

data class CheckPushTokenResult(
    val status: PushTokenStatus
)
