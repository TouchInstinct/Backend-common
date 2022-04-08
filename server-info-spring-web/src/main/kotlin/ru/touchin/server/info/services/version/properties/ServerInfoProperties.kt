package ru.touchin.server.info.services.version.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "server.info")
data class ServerInfoProperties(
    val buildVersion: String
)
