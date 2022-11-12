package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.apns

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import ru.touchin.push.message.provider.hpk.base.builders.Buildable

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy::class)
internal class Aps private constructor(
    @JsonProperty("alert")
    val alert: ApnsAlert?,
    val badge: Int?,
    val sound: String?,
    val contentAvailable: Int?,
    val category: String?,
    val threadId: String?,
) {

    class Validator {

        fun check(aps: Aps) {
            with(aps) {
                if (alert != null) {
                    ApnsAlert.validator.check(alert)
                }
            }
        }

    }

    class Builder : Buildable {

        private var apnsAlert: ApnsAlert? = null
        private var badge: Int? = null
        private var sound: String? = null
        private var contentAvailable: Int? = null
        private var category: String? = null
        private var threadId: String? = null

        fun setAlert(alert: ApnsAlert): Builder {
            this.apnsAlert = alert
            return this
        }

        fun setBadge(badge: Int): Builder {
            this.badge = badge
            return this
        }

        fun setSound(sound: String): Builder {
            this.sound = sound
            return this
        }

        fun setContentAvailable(contentAvailable: Int): Builder {
            this.contentAvailable = contentAvailable
            return this
        }

        fun setCategory(category: String): Builder {
            this.category = category
            return this
        }

        fun setThreadId(threadId: String): Builder {
            this.threadId = threadId
            return this
        }

        fun build(): Aps {
            return Aps(
                alert = apnsAlert,
                badge = badge,
                sound = sound,
                contentAvailable = contentAvailable,
                category = category,
                threadId = threadId,
            )
        }
    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()
    }

}
