package ru.touchin.push.message.provider.fcm.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import ru.touchin.push.message.provider.enums.PlatformType
import ru.touchin.push.message.provider.enums.PushMessageProviderType
import ru.touchin.push.message.provider.properties.PushMessageProviderProperties
import java.time.Duration

@ConstructorBinding
@ConfigurationProperties(prefix = "push-message-provider.fcm")
class PushMessageProviderFcmProperties(
    val appName: String,
    val auth: Auth.Credentials,
    val client: Client
) {

    sealed interface Auth {

        data class Credentials(
            val resourcePath: String
        ) : Auth

    }

    data class Client(
        val readTimeout: Duration,
        val connectionTimeout: Duration
    )

}
