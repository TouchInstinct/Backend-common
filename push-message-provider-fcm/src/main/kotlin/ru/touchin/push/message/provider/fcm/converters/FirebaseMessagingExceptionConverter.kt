package ru.touchin.push.message.provider.fcm.converters

import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.MessagingErrorCode
import org.springframework.stereotype.Component
import ru.touchin.common.exceptions.CommonException
import ru.touchin.push.message.provider.exceptions.InvalidPushTokenException
import ru.touchin.push.message.provider.exceptions.PushMessageProviderException

@Component
class FirebaseMessagingExceptionConverter {

    operator fun invoke(exception: FirebaseMessagingException): CommonException {
        return when (exception.messagingErrorCode) {
            MessagingErrorCode.INVALID_ARGUMENT,
            MessagingErrorCode.UNREGISTERED -> InvalidPushTokenException()
            else -> PushMessageProviderException(
                description = exception.message.orEmpty(),
                cause = exception
            )
        }
    }

}
