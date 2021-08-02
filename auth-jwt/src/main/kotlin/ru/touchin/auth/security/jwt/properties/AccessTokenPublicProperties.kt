package ru.touchin.auth.security.jwt.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm

data class AccessTokenKeyPair(val public: String)

@ConstructorBinding
@ConfigurationProperties(prefix = "token.access")
data class AccessTokenPublicProperties(
    val keyPair: AccessTokenKeyPair,
    val issuer: String,
    val signatureAlgorithm: SignatureAlgorithm
)
