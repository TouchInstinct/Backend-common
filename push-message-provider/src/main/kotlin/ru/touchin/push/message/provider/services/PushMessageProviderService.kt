package ru.touchin.push.message.provider.services

import ru.touchin.push.message.provider.dto.request.SendPushRequest
import ru.touchin.push.message.provider.dto.result.SendPushResult
import ru.touchin.push.message.provider.enums.PushMessageProviderType

interface PushMessageProviderService {

    val type: PushMessageProviderType

    fun send(request: SendPushRequest): SendPushResult

}
