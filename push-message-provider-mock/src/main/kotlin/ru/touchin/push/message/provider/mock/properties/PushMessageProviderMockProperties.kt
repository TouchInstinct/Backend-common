package ru.touchin.push.message.provider.mock.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConstructorBinding
@ConfigurationProperties(prefix = "push-message-provider.mock")
data class PushMessageProviderMockProperties(
    val sleepFor: Duration,
)
