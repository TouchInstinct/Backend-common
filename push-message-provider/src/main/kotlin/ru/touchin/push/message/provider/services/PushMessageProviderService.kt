package ru.touchin.push.message.provider.services

import ru.touchin.push.message.provider.dto.request.PushTokenCheck
import ru.touchin.push.message.provider.dto.request.SendPushRequest
import ru.touchin.push.message.provider.dto.result.CheckPushTokenResult
import ru.touchin.push.message.provider.dto.result.SendPushResult
import ru.touchin.push.message.provider.enums.PushMessageProviderType

interface PushMessageProviderService {

    val type: PushMessageProviderType

    fun send(request: SendPushRequest): SendPushResult

    fun check(request: PushTokenCheck): CheckPushTokenResult

}
