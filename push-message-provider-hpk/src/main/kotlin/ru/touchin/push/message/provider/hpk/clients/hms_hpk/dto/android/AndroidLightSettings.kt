package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.android

import com.fasterxml.jackson.annotation.JsonProperty
import ru.touchin.push.message.provider.hpk.base.builders.Buildable

internal data class AndroidLightSettings private constructor(
    /** Breathing light color. */
    @JsonProperty("color")
    val androidColor: AndroidColor,
    /** Interval when a breathing light is on */
    val lightOnDuration: String,
    /** Interval when a breathing light is off */
    val lightOffDuration: String,
) {

    class Validator {

        fun check(androidLightSettings: AndroidLightSettings) {
            with(androidLightSettings) {
                AndroidColor.validator.check(androidColor)

                require(
                    lightOnDuration.matches(LIGHT_DURATION_PATTERN)
                ) { "light_on_duration pattern is wrong" }
                require(
                    lightOffDuration.matches(LIGHT_DURATION_PATTERN)
                ) { "light_off_duration pattern is wrong" }
            }
        }

        private companion object {

            val LIGHT_DURATION_PATTERN: Regex = Regex("\\d+|\\d+[sS]|\\d+.\\d{1,9}|\\d+.\\d{1,9}[sS]")

        }

    }

    class Builder : Buildable {

        fun build(
            color: AndroidColor,
            lightOnDuration: String,
            lightOffDuration: String
        ): AndroidLightSettings {
            return AndroidLightSettings(
                androidColor = color,
                lightOnDuration = lightOnDuration,
                lightOffDuration = lightOffDuration,
            )
        }

    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }

}
