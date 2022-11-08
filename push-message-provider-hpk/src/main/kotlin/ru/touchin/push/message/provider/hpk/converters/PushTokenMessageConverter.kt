package ru.touchin.push.message.provider.hpk.converters

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import ru.touchin.push.message.provider.dto.request.PushTokenMessage
import ru.touchin.push.message.provider.hpk.base.extensions.ifNotNull
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.AndroidConfig
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.Message
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.android.AndroidClickAction
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.android.AndroidNotificationConfig
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android.AndroidClickActionType
import kotlin.jvm.Throws

@Component("push-message-provider.hpk.push-token-message-converter")
class PushTokenMessageConverter(
    private val pushMessageNotificationConverter: PushMessageNotificationConverter,
    @Qualifier("push-message-provider.hpk.client-objectmapper")
    private val objectMapper: ObjectMapper,
) {

    @Throws(IllegalArgumentException::class)
    internal operator fun invoke(request: PushTokenMessage): Message {
        return Message.builder()
            .addToken(request.token)
            .ifNotNull(request.data.takeIf(Map<*, *>::isNotEmpty)) { data ->
                setData(objectMapper.writeValueAsString(data))
            }
            .ifNotNull(request.pushMessageNotification) { notification ->
                setNotification(pushMessageNotificationConverter(notification))
            }
            .setupAndroidConfig()
            .build()
            .also { Message.validator.check(it) }
    }

    private fun Message.Builder.setupAndroidConfig(): Message.Builder {
        return setAndroidConfig(
            AndroidConfig.builder()
                .setAndroidNotificationConfig(
                    AndroidNotificationConfig.builder()
                        .setDefaultSound(USE_DEFAULT_SOUND)
                        .build(AndroidClickAction.builder().build(DEFAULT_ANDROID_CLICK_ACTION_TYPE))
                )
                .build()
        )
    }

    private companion object {

        const val USE_DEFAULT_SOUND = true
        val DEFAULT_ANDROID_CLICK_ACTION_TYPE = AndroidClickActionType.OPEN_APP

    }

}
