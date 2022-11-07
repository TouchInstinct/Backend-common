package ru.touchin.push.message.provider.hpk.clients.hms_hpk.requests

import ru.touchin.push.message.provider.hpk.clients.hms_hpk.bodies.HmsHpkMessagesSendBody

internal class HmsHpkMessagesSendRequest(
    val hmsHpkMessagesSendBody: HmsHpkMessagesSendBody,
    accessToken: String,
) : HmsHpkRequest(
    accessToken = accessToken,
)
