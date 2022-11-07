package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.web

import com.fasterxml.jackson.annotation.JsonProperty
import ru.touchin.push.message.provider.hpk.base.builders.Buildable
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.web.WebUrgency

internal data class WebPushHeaders private constructor(
    val ttl: String?,
    val topic: String?,
    @JsonProperty("urgency")
    val urgency: WebUrgency?,
) {

    class Validator {

        fun check(webpushHeaders: WebPushHeaders) {
            with(webpushHeaders) {
                if (ttl != null) {
                    require(ttl.matches(TTL_PATTERN)) { "Invalid ttl format" }
                }
            }
        }

        private companion object {

            val TTL_PATTERN: Regex = Regex("[0-9]+|[0-9]+[sS]")

        }

    }

    class Builder : Buildable {

        private var ttl: String? = null
        private var topic: String? = null
        private var urgency: WebUrgency? = null

        fun setTtl(ttl: String): Builder {
            this.ttl = ttl
            return this
        }

        fun setTopic(topic: String): Builder {
            this.topic = topic
            return this
        }

        fun setUrgency(urgency: WebUrgency): Builder {
            this.urgency = urgency
            return this
        }

        fun build(): WebPushHeaders {
            return WebPushHeaders(
                ttl = ttl,
                topic = topic,
                urgency = urgency,
            )
        }
    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }

}
