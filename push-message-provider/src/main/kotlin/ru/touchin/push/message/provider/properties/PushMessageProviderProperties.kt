package ru.touchin.push.message.provider.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import ru.touchin.push.message.provider.enums.PlatformType
import ru.touchin.push.message.provider.enums.PushMessageProviderType

@ConstructorBinding
@ConfigurationProperties(prefix = "push-message-provider")
class PushMessageProviderProperties(
    val platformProviders: Map<PlatformType, List<PushMessageProviderType>>
)
