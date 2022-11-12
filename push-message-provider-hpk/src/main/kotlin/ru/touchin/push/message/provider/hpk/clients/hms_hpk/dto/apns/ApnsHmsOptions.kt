package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.apns

import com.fasterxml.jackson.annotation.JsonProperty
import ru.touchin.push.message.provider.hpk.base.builders.Buildable
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android.AndroidTargetUserType

internal data class ApnsHmsOptions private constructor(
    @JsonProperty("target_user_type")
    val androidTargetUserType: AndroidTargetUserType,
) {

    class Validator {

        @Suppress("UNUSED_PARAMETER")
        fun check(apnsHmsOptions: ApnsHmsOptions) {
            // no validation
        }

    }

    class Builder : Buildable {

        fun build(androidTargetUserType: AndroidTargetUserType): ApnsHmsOptions {
            return ApnsHmsOptions(
                androidTargetUserType = androidTargetUserType,
            )
        }
    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }

}
