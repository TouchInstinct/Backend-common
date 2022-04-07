package ru.touchin.info.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "server.info")
data class ServerInfoProperties(
    val buildNumber: String
)
