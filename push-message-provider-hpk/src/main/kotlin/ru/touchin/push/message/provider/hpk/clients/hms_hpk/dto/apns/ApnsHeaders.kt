package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.apns

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import ru.touchin.push.message.provider.hpk.base.builders.Buildable
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.apns.ApnsPriority

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy::class)
internal data class ApnsHeaders private constructor(
    val authorization: String?,
    val apnsId: String?,
    val apnsExpiration: Long?,
    @JsonProperty("apns-priority")
    val apnsPriority: ApnsPriority?,
    val apnsTopic: String?,
    val apnsCollapseId: String?,
) {

    class Validator {

        fun check(apnsHeaders: ApnsHeaders) {
            with(apnsHeaders) {
                if (authorization != null) {
                    require(
                        authorization.startsWith(AUTHORIZATION_PATTERN)
                    ) { "authorization must start with bearer" }
                }
                if (apnsId != null) {
                    require(apnsId.matches(APN_ID_PATTERN)) { "apns-id format error" }
                }
                if (apnsCollapseId != null) {
                    require(
                        apnsCollapseId.toByteArray().size < APNS_COLLAPSE_ID_MAX_SIZE
                    ) { "Number of apnsCollapseId bytes must be less than $APNS_COLLAPSE_ID_MAX_SIZE" }
                }
            }
        }

        private companion object {

            const val AUTHORIZATION_PATTERN: String = "bearer"
            val APN_ID_PATTERN: Regex = Regex("[0-9a-z]{8}(-[0-9a-z]{4}){3}-[0-9a-z]{12}")
            const val APNS_COLLAPSE_ID_MAX_SIZE: Byte = 64

        }

    }

    class Builder : Buildable {

        private var authorization: String? = null
        private var apnsId: String? = null
        private var apnsExpiration: Long? = null
        private var apnsPriority: ApnsPriority? = null
        private var apnsTopic: String? = null
        private var apnsCollapseId: String? = null

        fun setAuthorization(authorization: String): Builder {
            this.authorization = authorization
            return this
        }

        fun setApnsId(apnsId: String): Builder {
            this.apnsId = apnsId
            return this
        }

        fun setApnsExpiration(apnsExpiration: Long): Builder {
            this.apnsExpiration = apnsExpiration
            return this
        }

        fun setApnsPriority(apnsPriority: ApnsPriority): Builder {
            this.apnsPriority = apnsPriority
            return this
        }

        fun setApnsTopic(apnsTopic: String): Builder {
            this.apnsTopic = apnsTopic
            return this
        }

        fun setApnsCollapseId(apnsCollapseId: String): Builder {
            this.apnsCollapseId = apnsCollapseId
            return this
        }

        fun build(): ApnsHeaders {
            return ApnsHeaders(
                authorization = authorization,
                apnsId = apnsId,
                apnsExpiration = apnsExpiration,
                apnsPriority = apnsPriority,
                apnsTopic = apnsTopic,
                apnsCollapseId = apnsCollapseId,
            )
        }
    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }

}
