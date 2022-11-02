package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.android

import com.fasterxml.jackson.annotation.JsonProperty
import ru.touchin.push.message.provider.hpk.base.builders.Buildable

internal data class AndroidBadgeNotification private constructor(
    /** Accumulative badge number. */
    val addNum: Short?,
    /** Full path of the app entry activity class. */
    @JsonProperty("class")
    val clazz: String,
    /** Badge number. Overrides [addNum]. */
    val setNum: Short?,
) {

    class Validator {

        fun check(androidBadgeNotification: AndroidBadgeNotification) {
            with(androidBadgeNotification) {
                if (addNum != null) {
                    require(
                        addNum in 1..99
                    ) { "add_num must locate between 0 and 100" }
                }

                if (setNum != null) {
                    require(
                        setNum in 0..99
                    ) { "set_num must locate between -1 and 100" }
                }
            }
        }

    }

    class Builder : Buildable {

        private var addNum: Short? = null
        private var setNum: Short? = null

        fun setAddNum(addNum: Short): Builder {
            this.addNum = addNum
            return this
        }

        fun setSetNum(setNum: Short): Builder {
            this.setNum = setNum
            return this
        }

        fun build(badgeClass: String): AndroidBadgeNotification {
            return AndroidBadgeNotification(
                addNum = addNum,
                clazz = badgeClass,
                setNum = setNum,
            )
        }
    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }
}
