package ru.touchin.auth.security.metadata.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "metadata")
data class MetadataProperties(
    val tokenEndpoint: String?,
    val responseTypesSupported: List<String> = emptyList(),
)
