package ru.touchin.auth.security.oauth2.metadata.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "oauth2-metadata")
data class OAuth2MetadataProperties(
    val tokenEndpoint: String?,
    val responseTypesSupported: List<String> = emptyList(),
)
