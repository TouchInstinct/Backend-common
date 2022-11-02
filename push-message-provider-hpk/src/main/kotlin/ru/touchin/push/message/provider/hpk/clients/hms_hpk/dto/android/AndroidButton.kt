package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.android

import com.fasterxml.jackson.annotation.JsonProperty
import ru.touchin.push.message.provider.hpk.base.builders.Buildable
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android.AndroidActionType
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android.AndroidIntentType

internal data class AndroidButton private constructor(
    /** Button name. */
    val name: String,
    /** Button action. */
    @JsonProperty("action_type")
    val androidActionType: AndroidActionType,
    /** Method of opening a custom app page. */
    @JsonProperty("intent_type")
    val androidIntentType: AndroidIntentType?,
    val intent: String?,
    /** Map of key-values. */
    val data: String?,
) {

    class Validator {

        fun check(androidButton: AndroidButton) {
            with(androidButton) {
                require(
                    name.length < 40
                ) { "Button name length cannot exceed 40" }

                if (androidActionType == AndroidActionType.SHARE_NOTIFICATION_MESSAGE) {
                    require(!data.isNullOrEmpty()) { "Data is needed when actionType is $androidActionType" }
                    require(data.length < 1024) { "Data length cannot exceed 1024 chars" }
                }
            }
        }

    }

    class Builder : Buildable {

        private var intent: String? = null
        private var data: String? = null

        fun setIntent(intent: String): Builder {
            this.intent
            return this
        }

        fun setData(data: String): Builder {
            this.data = data
            return this
        }

        fun build(name: String, androidActionType: AndroidActionType, androidIntentType: AndroidIntentType): AndroidButton {
            return AndroidButton(
                name = name,
                androidActionType = androidActionType,
                androidIntentType = androidIntentType,
                intent = intent,
                data = data
            )
        }
    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }

}
