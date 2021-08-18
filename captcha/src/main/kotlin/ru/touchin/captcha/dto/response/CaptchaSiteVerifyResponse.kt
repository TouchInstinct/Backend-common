package ru.touchin.captcha.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

data class CaptchaSiteVerifyResponse(
    val score: Double,
    val action: String,
    val success: Boolean,
    @JsonProperty("challenge_ts")
    val challengeTs: ZonedDateTime
)
