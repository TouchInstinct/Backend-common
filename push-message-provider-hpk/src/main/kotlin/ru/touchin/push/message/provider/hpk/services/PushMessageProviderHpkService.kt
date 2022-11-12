package ru.touchin.push.message.provider.hpk.services

import org.springframework.stereotype.Service
import ru.touchin.push.message.provider.dto.request.PushTokenCheck
import ru.touchin.push.message.provider.dto.request.PushTokenMessage
import ru.touchin.push.message.provider.dto.request.SendPushRequest
import ru.touchin.push.message.provider.dto.result.CheckPushTokenResult
import ru.touchin.push.message.provider.dto.result.SendPushResult
import ru.touchin.push.message.provider.dto.result.SendPushTokenMessageResult
import ru.touchin.push.message.provider.enums.PushMessageProviderType
import ru.touchin.push.message.provider.exceptions.InvalidPushTokenException
import ru.touchin.push.message.provider.exceptions.PushMessageProviderException
import ru.touchin.push.message.provider.services.PushMessageProviderService

@Service
class PushMessageProviderHpkService(
    private val hmsHpkClientService: HmsHpkClientService,
) : PushMessageProviderService {

    override val type: PushMessageProviderType = PushMessageProviderType.HPK

    @Throws(PushMessageProviderException::class, InvalidPushTokenException::class)
    override fun send(request: SendPushRequest): SendPushResult {
        when (request) {
            is PushTokenMessage -> hmsHpkClientService.send(request)
        }

        return SendPushTokenMessageResult
    }

    override fun check(request: PushTokenCheck): CheckPushTokenResult {
        val result = hmsHpkClientService.check(request)

        return CheckPushTokenResult(result)
    }

}
