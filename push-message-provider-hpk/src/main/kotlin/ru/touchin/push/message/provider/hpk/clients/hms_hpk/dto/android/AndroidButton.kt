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
                    name.length <= NAME_MAX_LENGTH
                ) { "Button name length cannot exceed $NAME_MAX_LENGTH" }

                if (androidActionType == AndroidActionType.SHARE_NOTIFICATION_MESSAGE) {
                    require(!data.isNullOrEmpty()) { "Data is needed when actionType is $androidActionType" }
                    require(data.length <= DATA_MAX_LENGTH) { "Data length cannot exceed $DATA_MAX_LENGTH chars" }
                }
            }
        }

        private companion object {

            const val NAME_MAX_LENGTH: Byte = 40
            const val DATA_MAX_LENGTH: Short = 1024

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
