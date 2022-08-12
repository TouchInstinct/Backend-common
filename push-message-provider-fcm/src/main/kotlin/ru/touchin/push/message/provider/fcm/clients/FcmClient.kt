package ru.touchin.push.message.provider.fcm.clients

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import org.springframework.stereotype.Component
import ru.touchin.push.message.provider.dto.request.PushTokenMessage
import ru.touchin.push.message.provider.dto.result.SendPushResult
import ru.touchin.push.message.provider.dto.result.SendPushTokenMessageResult
import ru.touchin.push.message.provider.exceptions.InvalidPushTokenException
import ru.touchin.push.message.provider.exceptions.PushMessageProviderException
import ru.touchin.push.message.provider.fcm.converters.FirebaseMessagingExceptionConverter
import ru.touchin.push.message.provider.fcm.converters.PushTokenMessageConverter

/**
 * Service that provides integration with FCM.
 * @see <a href="https://firebase.google.com/docs/cloud-messaging">FCM documentation</a>
 */
@Component
class FcmClient(
    private val firebaseMessaging: FirebaseMessaging,
    private val pushTokenMessageConverter: PushTokenMessageConverter,
    private val firebaseMessagingExceptionConverter: FirebaseMessagingExceptionConverter
) {

    @Throws(PushMessageProviderException::class, InvalidPushTokenException::class)
    fun sendPushTokenMessage(request: PushTokenMessage): SendPushResult {
        val message = pushTokenMessageConverter(request)

        return try {
            val messageId = firebaseMessaging.send(message)

            SendPushTokenMessageResult(messageId)
        } catch (e: FirebaseMessagingException) {
            throw firebaseMessagingExceptionConverter(e)
        }
    }

}
