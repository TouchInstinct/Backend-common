package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto

import com.fasterxml.jackson.annotation.JsonProperty
import ru.touchin.push.message.provider.hpk.base.builders.Buildable
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.android.AndroidNotificationConfig
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android.AndroidFastAppTargetType
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android.AndroidUrgency

internal data class AndroidConfig private constructor(
    /**
     * Mode for the Push Kit server to cache messages sent to an offline device.
     * These cached messages will be delivered once the device goes online again.
     * The options are as follows:
     *
     * 0: Only the latest message sent by each app to the user device is cached.
     *
     * -1: All messages are cached.
     *
     * 1-100: message cache group ID. Messages are cached by group. Each group can cache only one message for each app.
     *
     * For example, if you send 10 messages and set collapse_key to 1 for the first five messages and to 2 for the rest, the latest message whose value of collapse_key is 1 and the latest message whose value of collapse_key is 2 are sent to the user after the user's device goes online.
     *
     * The default value is -1.
     * */
    val collapseKey: Short?,
    @JsonProperty("urgency")
    val androidUrgency: AndroidUrgency?,
    val category: String?,
    /**
     * Message cache duration, in seconds.
     * When a user device is offline, the Push Kit server caches messages.
     * If the user device goes online within the message cache time, the cached messages are delivered.
     * Otherwise, the messages are discarded.
     * */
    val ttl: String?,
    /**
     * Tag of a message in a batch delivery task.
     * The tag is returned to your server when Push Kit sends the message receipt.
     * */
    val biTag: String?,
    /** State of a mini program when a quick app sends a data message. Default is [AndroidFastAppTargetType.PRODUCTION]*/
    @JsonProperty("fast_app_target")
    val androidFastAppTargetType: AndroidFastAppTargetType?,
    /** Custom message payload. If the data parameter is set, the value of the [Message.data] field is overwritten. */
    val data: String?,
    @JsonProperty("notification")
    val androidNotificationConfig: AndroidNotificationConfig?,
    /** Unique receipt ID that associates with the receipt URL and configuration of the downlink message. */
    val receiptId: String?,
) {

    class Validator {

        fun check(androidConfig: AndroidConfig, notification: Notification?) {
            with(androidConfig) {
                if (collapseKey != null) {
                    require(collapseKey in -1..100) { "Collapse Key should be [-1, 100]" }
                }
                if (ttl != null) {
                    require(ttl.matches(TTL_PATTERN)) { "The TTL's format is wrong" }
                }
                if (androidNotificationConfig != null) {
                    AndroidNotificationConfig.validator.check(androidNotificationConfig, notification)
                }
            }
        }

        private companion object {

            val TTL_PATTERN: Regex = Regex("\\d+|\\d+[sS]|\\d+.\\d{1,9}|\\d+.\\d{1,9}[sS]")

        }

    }

    class Builder : Buildable {

        private var collapseKey: Short? = null
        private var androidUrgency: AndroidUrgency? = null
        private var category: String? = null
        private var ttl: String? = null
        private var biTag: String? = null
        private var androidFastAppTargetType: AndroidFastAppTargetType? = null
        private var data: String? = null
        private var androidNotificationConfig: AndroidNotificationConfig? = null
        private var receiptId: String? = null

        fun setCollapseKey(collapseKey: Short): Builder {
            this.collapseKey = collapseKey
            return this
        }

        fun setUrgency(androidUrgency: AndroidUrgency): Builder {
            this.androidUrgency = androidUrgency
            return this
        }

        fun setCategory(category: String?): Builder {
            this.category = category
            return this
        }

        fun setTtl(ttl: String): Builder {
            this.ttl = ttl
            return this
        }

        fun setBiTag(biTag: String): Builder {
            this.biTag = biTag
            return this
        }

        fun setFastAppTargetType(androidFastAppTargetType: AndroidFastAppTargetType): Builder {
            this.androidFastAppTargetType = androidFastAppTargetType
            return this
        }

        fun setData(data: String): Builder {
            this.data = data
            return this
        }

        fun setAndroidNotificationConfig(androidNotificationConfig: AndroidNotificationConfig): Builder {
            this.androidNotificationConfig = androidNotificationConfig
            return this
        }

        fun setReceiptId(receiptId: String): Builder {
            this.receiptId = receiptId
            return this
        }

        fun build(): AndroidConfig {
            return AndroidConfig(
                collapseKey = collapseKey,
                androidUrgency = androidUrgency,
                category = category,
                ttl = ttl,
                biTag = biTag,
                androidFastAppTargetType = androidFastAppTargetType,
                data = data,
                androidNotificationConfig = androidNotificationConfig,
                receiptId = receiptId,
            )
        }

    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }

}
