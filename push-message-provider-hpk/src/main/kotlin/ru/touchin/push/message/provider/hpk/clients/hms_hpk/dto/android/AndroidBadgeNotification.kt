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
                        addNum in ADD_NUM_MIN_VALUE..ADD_NUM_MAX_VALUE
                    ) { "add_num must locate in $ADD_NUM_MIN_VALUE and $ADD_NUM_MAX_VALUE" }
                }

                if (setNum != null) {
                    require(
                        setNum in SET_NUM_RANGE_CONSTRAINT
                    ) { "set_num must locate between $SET_NUM_MIN_VALUE and $SET_NUM_MAX_VALUE" }
                }
            }
        }

        private companion object {

            const val ADD_NUM_MIN_VALUE: Byte = 1
            const val ADD_NUM_MAX_VALUE: Byte = 99
            val ADD_NUM_RANGE_CONSTRAINT: IntRange = ADD_NUM_MIN_VALUE..ADD_NUM_MAX_VALUE
            const val SET_NUM_MIN_VALUE: Byte = 0
            const val SET_NUM_MAX_VALUE: Byte = 99
            val SET_NUM_RANGE_CONSTRAINT: IntRange = SET_NUM_MIN_VALUE..SET_NUM_MAX_VALUE

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
