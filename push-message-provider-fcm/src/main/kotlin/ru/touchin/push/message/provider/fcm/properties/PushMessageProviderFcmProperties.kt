package ru.touchin.push.message.provider.fcm.properties

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.util.Assert
import java.time.Duration
import java.util.*

@ConstructorBinding
@ConfigurationProperties(prefix = "push-message-provider.fcm")
class PushMessageProviderFcmProperties(
    val appName: String,
    val auth: Auth,
    val client: Client,
) {

    data class Auth(
        val credentialsFile: CredentialsFile?,
        val credentialsData: CredentialsData?,
        val token: AccessToken?,
    ) {

        init {
            Assert.notNull(
                arrayOf(credentialsFile, credentialsData, token).mapNotNull { it }.firstOrNull(),
                "Authorization configuration is not provided."
            )

            Assert.notNull(
                arrayOf(credentialsFile, credentialsData, token).mapNotNull { it }.singleOrNull(),
                "Authorization must be configured using only single provider."
            )
        }

    }

    data class CredentialsFile(
        val path: String
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class CredentialsData(
        val type: String,
        val projectId: String,
        val privateKeyId: String,
        val privateKey: String,
        val clientEmail: String,
        val clientId: String,
        val authUri: String,
        val tokenUri: String,
        val authProviderX509CertUrl: String,
        val clientX509CertUrl: String
    )

    data class AccessToken(
        val value: String,
        val expiresAt: Date
    )

    data class Client(
        val readTimeout: Duration,
        val connectionTimeout: Duration
    )

}
