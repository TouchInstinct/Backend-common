package ru.touchin.push.message.provider.hpk.clients.hms_oauth.response

internal class HmsOauthTokenResponse(
    val tokenType: String,
    val accessToken: String,
    /** Expiration in seconds. */
    val expiresIn: Long,
)
