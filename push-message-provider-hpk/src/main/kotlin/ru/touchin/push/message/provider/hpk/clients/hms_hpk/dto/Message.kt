package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto

import com.fasterxml.jackson.annotation.JsonProperty
import ru.touchin.push.message.provider.hpk.base.builders.Buildable

internal data class Message private constructor(
    /** Custom message payload. Map of key-values */
    val data: String?,
    /** Notification message content. */
    @JsonProperty("notification")
    val notification: Notification?,
    /** Android message push control. */
    @JsonProperty("android")
    val android: AndroidConfig?,
    @JsonProperty("apns")
    val apns: ApnsConfig?,
    @JsonProperty("webpush")
    val webpush: WebPushConfig?,
    /** Push token of the target user of a message. */
    val token: Collection<String>?,
    /** Topic subscribed by the target user of a message. */
    val topic: String?,
    val condition: String?,
) {

    class Validator {

        fun check(message: Message) {
            with(message) {
                require(
                    arrayOf(
                        !token.isNullOrEmpty(),
                        !topic.isNullOrBlank(),
                        !condition.isNullOrBlank(),
                    ).count { it } == 1
                ) { "Exactly one of token, topic or condition must be specified" }

                if (token != null) {
                    require(
                        token.size in TOKENS_SIZE_RANGE_CONSTRAINT
                    ) { "Number of tokens, if specified, must be from $TOKENS_SIZE_MIN to $TOKENS_SIZE_MAX" }
                }

                notification?.also { Notification.validator.check(it) }
                android?.also { AndroidConfig.validator.check(it, notification) }
                apns?.also { ApnsConfig.validator.check(it) }
                webpush?.also { WebPushConfig.validator.check(it) }
            }
        }

        private companion object {

            const val TOKENS_SIZE_MIN: Byte = 1
            const val TOKENS_SIZE_MAX: Short = 1000
            val TOKENS_SIZE_RANGE_CONSTRAINT: IntRange = TOKENS_SIZE_MIN..TOKENS_SIZE_MAX

        }

    }

    class Builder : Buildable {

        private var data: String? = null
        private var notification: Notification? = null
        private var androidConfig: AndroidConfig? = null
        private var apns: ApnsConfig? = null
        private var webpush: WebPushConfig? = null
        private val token: MutableList<String> = mutableListOf()
        private var topic: String? = null
        private var condition: String? = null

        fun setData(data: String): Builder {
            this.data = data
            return this
        }

        fun setNotification(notification: Notification): Builder {
            this.notification = notification
            return this
        }

        fun setAndroidConfig(androidConfig: AndroidConfig): Builder {
            this.androidConfig = androidConfig
            return this
        }

        fun setApns(apns: ApnsConfig): Builder {
            this.apns = apns
            return this
        }

        fun setWebpush(webpush: WebPushConfig): Builder {
            this.webpush = webpush
            return this
        }

        fun addToken(vararg token: String): Builder {
            this.token.addAll(token)
            return this
        }

        fun setTopic(topic: String): Builder {
            this.topic = topic
            return this
        }

        fun setCondition(condition: String): Builder {
            this.condition = condition
            return this
        }

        fun build(): Message {
            return Message(
                data = data,
                notification = notification,
                android = androidConfig,
                apns = apns,
                webpush = webpush,
                topic = topic,
                condition = condition,
                token = token.takeIf(Collection<*>::isNotEmpty),
            )
        }

    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }

}
