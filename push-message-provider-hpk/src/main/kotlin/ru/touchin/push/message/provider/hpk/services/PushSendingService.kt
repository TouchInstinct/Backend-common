package ru.touchin.push.message.provider.hpk.services

import org.springframework.stereotype.Service
import ru.touchin.push.message.provider.dto.PushMessageNotification
import ru.touchin.push.message.provider.dto.request.PushTokenCheck
import ru.touchin.push.message.provider.dto.request.PushTokenMessage
import ru.touchin.push.message.provider.enums.PlatformType
import ru.touchin.push.message.provider.enums.PushTokenStatus
import ru.touchin.push.message.provider.factories.PushMessageProviderServiceFactory
import ru.touchin.push.message.provider.services.PushMessageProviderService

@Service
class PushSendingService(
    private val pushMessageProviderServiceFactory: PushMessageProviderServiceFactory
) {

    fun sendPushMessage() {
        val yourPushToken = "pushTokenForChecking"
        val platform = PlatformType.ANDROID_GOOGLE

        val pushMessageProvider: PushMessageProviderService = pushMessageProviderServiceFactory.get(platform)

        val result = pushMessageProvider.check( // Проверка валидности токена для обозначения целесообразности отправки
            PushTokenCheck(
                pushToken = yourPushToken
            )
        )

        if (result.status == PushTokenStatus.VALID) { // Токен валиден, PushMessageProviderService интегрирован в систему
            // Отправка пуш-уведомления
            pushMessageProvider.send(
                PushTokenMessage(
                    token = yourPushToken,
                    pushMessageNotification = PushMessageNotification(
                        title = "Your PushMessage",
                        description = "Provided by PushMessageProviderService",
                        imageUrl = null
                    ),
                    data = mapOf(
                        "customKey" to "customData"
                    )
                )
            )
        }
    }

}
