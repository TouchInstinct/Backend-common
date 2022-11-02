package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.android

import com.fasterxml.jackson.annotation.JsonProperty
import ru.touchin.push.message.provider.hpk.base.builders.Buildable
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android.AndroidClickActionType

internal data class AndroidClickAction private constructor(
    /** Message tapping action type. */
    @JsonProperty("type")
    val androidClickActionType: AndroidClickActionType,
    val intent: String?,
    /** URL to be opened. */
    val url: String?,
    /** Action corresponding to the activity of the page to be opened when the custom app page is opened through the action. */
    val action: String?,
) {

    class Validator {

        fun check(androidClickAction: AndroidClickAction) {
            with(androidClickAction) {
                when (androidClickActionType) {
                    AndroidClickActionType.CUSTOMIZE_ACTION -> require(
                        !intent.isNullOrBlank() || !action.isNullOrBlank()
                    ) { "intent or action is required when click type is $androidClickActionType" }

                    AndroidClickActionType.OPEN_URL -> {
                        require(!url.isNullOrBlank()) { "url is required when click type is $androidClickActionType" }
                        require(url.matches(HTTPS_PATTERN)) { "url must start with https" }
                    }

                    AndroidClickActionType.OPEN_APP -> {
                        // no verification
                    }
                }
            }
        }

        private companion object {

            val HTTPS_PATTERN: Regex = Regex("^https.*")

        }

    }

    class Builder : Buildable {

        private var intent: String? = null
        private var url: String? = null
        private var richResource: String? = null
        private var action: String? = null

        fun setIntent(intent: String): Builder {
            this.intent = intent
            return this
        }

        fun setUrl(url: String): Builder {
            this.url = url
            return this
        }

        fun setRichResource(richResource: String): Builder {
            this.richResource = richResource
            return this
        }

        fun setAction(action: String): Builder {
            this.action = action
            return this
        }

        fun build(androidClickActionType: AndroidClickActionType): AndroidClickAction {
            return AndroidClickAction(
                androidClickActionType = androidClickActionType,
                intent = intent.takeIf { androidClickActionType == AndroidClickActionType.CUSTOMIZE_ACTION },
                action = action.takeIf { androidClickActionType == AndroidClickActionType.CUSTOMIZE_ACTION },
                url = url.takeIf { androidClickActionType == AndroidClickActionType.OPEN_URL },
            )
        }

    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }

}
