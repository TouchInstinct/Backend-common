package ru.touchin.push.message.provider.hpk.services

import ru.touchin.push.message.provider.dto.request.PushTokenCheck
import ru.touchin.push.message.provider.dto.request.PushTokenMessage
import ru.touchin.push.message.provider.enums.PushTokenStatus

interface HmsHpkClientService {

    fun send(request: PushTokenMessage)

    fun check(request: PushTokenCheck): PushTokenStatus

}
