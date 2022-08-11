package ru.touchin.push.message.provider.fcm.services

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import org.springframework.stereotype.Service
import ru.touchin.push.message.provider.dto.request.PushTokenMessage
import ru.touchin.push.message.provider.dto.request.SendPushRequest
import ru.touchin.push.message.provider.dto.result.SendPushResult
import ru.touchin.push.message.provider.dto.result.SendPushTokenMessageResult
import ru.touchin.push.message.provider.enums.PushMessageProviderType
import ru.touchin.push.message.provider.exceptions.InvalidPushTokenException
import ru.touchin.push.message.provider.exceptions.PushMessageProviderException
import ru.touchin.push.message.provider.fcm.converters.FirebaseMessagingExceptionConverter
import ru.touchin.push.message.provider.fcm.converters.PushTokenMessageConverter
import ru.touchin.push.message.provider.services.PushMessageProviderService

/**
 * Service that provides integration with FCM.
 * @see <a href="https://firebase.google.com/docs/cloud-messaging">FCM documentation</a>
 */
@Service
class PushMessageProviderFcmService(
    private val firebaseMessaging: FirebaseMessaging,
    private val pushTokenMessageConverter: PushTokenMessageConverter,
    private val firebaseMessagingExceptionConverter: FirebaseMessagingExceptionConverter
) : PushMessageProviderService {

    override val type: PushMessageProviderType = PushMessageProviderType.FCM

    @Throws(PushMessageProviderException::class, InvalidPushTokenException::class)
    override fun send(request: SendPushRequest): SendPushResult {
        return when (request) {
            is PushTokenMessage -> sendPushTokenMessage(request)
        }
    }

    private fun sendPushTokenMessage(request: PushTokenMessage): SendPushResult {
        val message = pushTokenMessageConverter(request)

        return try {
            val messageId = firebaseMessaging.send(message)

            SendPushTokenMessageResult(messageId)
        } catch (e: FirebaseMessagingException) {
            throw firebaseMessagingExceptionConverter(e)
        }
    }

}
