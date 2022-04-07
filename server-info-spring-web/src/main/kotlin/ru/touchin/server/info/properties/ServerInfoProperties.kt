package ru.touchin.server.info.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "server.info")
data class ServerInfoProperties(
    val buildVersion: String
)
