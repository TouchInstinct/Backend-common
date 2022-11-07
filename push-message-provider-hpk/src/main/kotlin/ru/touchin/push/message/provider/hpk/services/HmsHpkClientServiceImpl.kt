package ru.touchin.push.message.provider.hpk.services

import org.springframework.stereotype.Service
import ru.touchin.push.message.provider.dto.request.PushTokenCheck
import ru.touchin.push.message.provider.dto.request.PushTokenMessage
import ru.touchin.push.message.provider.enums.PushTokenStatus
import ru.touchin.push.message.provider.exceptions.InvalidPushTokenException
import ru.touchin.push.message.provider.exceptions.PushMessageProviderException
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.HmsHpkWebClient
import ru.touchin.push.message.provider.hpk.clients.hms.enums.HmsResponseCode
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.bodies.HmsHpkMessagesSendBody
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.requests.HmsHpkMessagesSendRequest
import ru.touchin.push.message.provider.hpk.converters.PushTokenMessageConverter

@Service
class HmsHpkClientServiceImpl(
    private val hmsHpkWebClient: HmsHpkWebClient,
    private val hmsOauthClientService: HmsOauthClientService,
    private val pushTokenMessageConverter: PushTokenMessageConverter,
) : HmsHpkClientService {

    override fun send(request: PushTokenMessage) {
        sendToPushToken(request, dryRun = false)
    }

    override fun check(request: PushTokenCheck): PushTokenStatus {
        val validationRequest = PushTokenMessage(
            token = request.pushToken,
            pushMessageNotification = null,
            data = emptyMap()
        )

        return try {
            sendToPushToken(validationRequest, dryRun = false)

            PushTokenStatus.VALID
        } catch (ipte: InvalidPushTokenException) {
            PushTokenStatus.INVALID
        } catch (pmpe: PushMessageProviderException) {
            PushTokenStatus.UNKNOWN
        }
    }

    @Throws(PushMessageProviderException::class, InvalidPushTokenException::class)
    private fun sendToPushToken(request: PushTokenMessage, dryRun: Boolean) {
        val accessToken = hmsOauthClientService.getAccessToken()

        val result = hmsHpkWebClient.messagesSend(
            HmsHpkMessagesSendRequest(
                hmsHpkMessagesSendBody = HmsHpkMessagesSendBody(
                    validateOnly = dryRun,
                    message = pushTokenMessageConverter(request),
                ),
                accessToken = accessToken,
            )
        )

        when (HmsResponseCode.fromCode(result.code)) {
            HmsResponseCode.INVALID_TOKEN,
            HmsResponseCode.PERMISSION_DENIED -> {
                throw InvalidPushTokenException()
            }

            else -> {
                throw PushMessageProviderException(result.msg, null)
            }
        }
    }

}
