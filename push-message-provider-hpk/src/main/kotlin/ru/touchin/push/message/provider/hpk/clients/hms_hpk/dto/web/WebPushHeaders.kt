package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.web

import ru.touchin.push.message.provider.hpk.base.builders.Buildable

internal data class WebPushHeaders private constructor(
    val ttl: String?,
    val topic: String?,
    val urgency: String?,
) {

    class Validator {

        fun check(webpushHeaders: WebPushHeaders) {
            with(webpushHeaders) {
                if (ttl != null) {
                    require(ttl.matches(TTL_PATTERN)) { "Invalid ttl format" }
                }
                if (urgency != null) {
                    require(
                        URGENCY_VALUE.all { it == urgency }
                    ) { "Invalid urgency" }
                }
            }
        }

        private companion object {

            val TTL_PATTERN: Regex = Regex("[0-9]+|[0-9]+[sS]")
            val URGENCY_VALUE: Array<String> = arrayOf("very-low", "low", "normal", "high")

        }

    }

    class Builder : Buildable {

        private var ttl: String? = null
        private var topic: String? = null
        private var urgency: String? = null

        fun setTtl(ttl: String): Builder {
            this.ttl = ttl
            return this
        }

        fun setTopic(topic: String): Builder {
            this.topic = topic
            return this
        }

        fun setUrgency(urgency: String): Builder {
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
