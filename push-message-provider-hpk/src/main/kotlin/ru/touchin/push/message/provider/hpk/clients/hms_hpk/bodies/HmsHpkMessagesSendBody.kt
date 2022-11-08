package ru.touchin.push.message.provider.hpk.clients.hms_hpk.bodies

import com.fasterxml.jackson.annotation.JsonProperty
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.Message

internal class HmsHpkMessagesSendBody(
    /** Send "dry" message without notification delivery */
    val validateOnly: Boolean,
    /** Message structure, which must contain the valid message payload and valid sending object. */
    @JsonProperty("message")
    val message: Message,
)
