package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto

import com.fasterxml.jackson.annotation.JsonProperty
import ru.touchin.push.message.provider.hpk.base.builders.Buildable
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.apns.ApnsHeaders
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.apns.ApnsHmsOptions
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.apns.Aps

internal data class ApnsConfig private constructor(
    @JsonProperty("hms_options")
    val apnsHmsOptions: ApnsHmsOptions?,
    @JsonProperty("apns_headers")
    val apnsHeaders: ApnsHeaders,
    val payload: Map<String, Any>,
) {

    class Validator {

        fun check(apnsConfig: ApnsConfig) {
            with(apnsConfig) {
                apnsHmsOptions?.also { ApnsHmsOptions.validator.check(it) }
                ApnsHeaders.validator.check(apnsHeaders)

                if (payload[APS_PAYLOAD_KEY] != null) {
                    val aps: Aps? = payload[APS_PAYLOAD_KEY] as Aps?

                    aps?.also { Aps.validator.check(it) }
                }
            }
        }

    }

    class Builder : Buildable {

        private var apnsHmsOptions: ApnsHmsOptions? = null
        private var payload: MutableMap<String, Any> = mutableMapOf()
        private var aps: Aps? = null

        fun setApnsHmsOptions(apnsHmsOptions: ApnsHmsOptions): Builder {
            this.apnsHmsOptions = apnsHmsOptions
            return this
        }

        fun addPayload(payload: Map<String, Any>): Builder {
            this.payload.putAll(payload)
            return this
        }

        fun setAps(aps: Aps): Builder {
            this.aps = aps
            payload[APS_PAYLOAD_KEY] = aps
            return this
        }

        fun build(apnsHeaders: ApnsHeaders): ApnsConfig {
            return ApnsConfig(
                apnsHmsOptions = apnsHmsOptions,
                apnsHeaders = apnsHeaders,
                payload = payload
            )
        }

    }

    companion object {

        const val APS_PAYLOAD_KEY = "aps"

        val validator = Validator()

        fun builder() = Builder()

    }

}
