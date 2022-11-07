package ru.touchin.push.message.provider.hpk.clients.hms_hpk.responses

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy::class)
internal class HmsHpkResponse(
    /** Result code. */
    val code: String,
    /** Result code description. */
    val msg: String,
    /** Request ID. */
    val requestId: String,
)
