package ru.touchin.push.message.provider.fcm.services

import org.springframework.stereotype.Service
import ru.touchin.push.message.provider.dto.request.PushTokenCheck
import ru.touchin.push.message.provider.dto.request.PushTokenMessage
import ru.touchin.push.message.provider.dto.request.SendPushRequest
import ru.touchin.push.message.provider.dto.result.CheckPushTokenResult
import ru.touchin.push.message.provider.dto.result.SendPushResult
import ru.touchin.push.message.provider.enums.PushMessageProviderType
import ru.touchin.push.message.provider.exceptions.InvalidPushTokenException
import ru.touchin.push.message.provider.exceptions.PushMessageProviderException
import ru.touchin.push.message.provider.fcm.clients.FcmClient
import ru.touchin.push.message.provider.services.PushMessageProviderService

@Service
class PushMessageProviderFcmService(
    private val fcmClient: FcmClient
) : PushMessageProviderService {

    override val type: PushMessageProviderType = PushMessageProviderType.FCM

    @Throws(PushMessageProviderException::class, InvalidPushTokenException::class)
    override fun send(request: SendPushRequest): SendPushResult {
        return when (request) {
            is PushTokenMessage -> fcmClient.sendPushTokenMessage(request)
        }
    }

    override fun check(request: PushTokenCheck): CheckPushTokenResult {
        val status = fcmClient.check(request)

        return CheckPushTokenResult(status)
    }

}
