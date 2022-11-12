package ru.touchin.push.message.provider.hpk.clients.hms_oauth.response

internal class HmsOauthErrorResponse(
    val error: Int,
    val subError: Int,
    val errorDescription: String,
)
