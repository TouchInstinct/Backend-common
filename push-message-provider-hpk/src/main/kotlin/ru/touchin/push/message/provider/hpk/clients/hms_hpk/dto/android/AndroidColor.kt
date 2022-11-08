package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.android

import ru.touchin.push.message.provider.hpk.base.builders.Buildable

internal data class AndroidColor private constructor(
    /** Alpha setting of the RGB color.*/
    val alpha: Float,
    /** Red setting of the RGB color. */
    val red: Float,
    /** Green setting of the RGB color. */
    val green: Float,
    /** Green setting of the RGB color. */
    val blue: Float,
) {

    class Validator {

        fun check(androidColor: AndroidColor) {
            with(androidColor) {
                require(alpha in COLOR_RANGE_CONSTRAINT) { "Alpha must be locate between [0,1]" }
                require(red in COLOR_RANGE_CONSTRAINT) { "Red must be locate between [0,1]" }
                require(green in COLOR_RANGE_CONSTRAINT) { "Green must be locate between [0,1]" }
                require(blue in COLOR_RANGE_CONSTRAINT) { "Blue must be locate between [0,1]" }
            }
        }

        private companion object {

            private const val ZERO: Float = 0.0f
            private const val ONE: Float = 1.0f
            val COLOR_RANGE_CONSTRAINT = ZERO..ONE

        }

    }

    class Builder : Buildable {

        private var alpha: Float = 1.0f
        private var red: Float = 0.0f
        private var green: Float = 0.0f
        private var blue: Float = 0.0f

        fun setAlpha(alpha: Float): Builder {
            this.alpha = alpha
            return this
        }

        fun setRed(red: Float): Builder {
            this.red = red
            return this
        }

        fun setGreen(green: Float): Builder {
            this.green = green
            return this
        }

        fun setBlue(blue: Float): Builder {
            this.blue = blue
            return this
        }

        fun build(): AndroidColor {
            return AndroidColor(
                alpha = alpha,
                red = red,
                green = green,
                blue = blue
            )
        }
    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }

}
