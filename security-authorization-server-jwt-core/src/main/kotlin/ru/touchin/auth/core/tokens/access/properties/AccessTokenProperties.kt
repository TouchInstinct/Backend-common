package ru.touchin.auth.core.tokens.access.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import java.time.Duration

data class AccessTokenKeyPair(val public: String, val private: String)

@ConstructorBinding
@ConfigurationProperties(prefix = "token.access")
data class AccessTokenProperties(
    val keyPair: AccessTokenKeyPair,
    val issuer: String,
    val timeToLive: Duration,
    val signatureAlgorithm: SignatureAlgorithm
)
