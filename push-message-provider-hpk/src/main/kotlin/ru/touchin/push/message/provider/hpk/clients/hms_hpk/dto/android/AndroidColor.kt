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
                require(alpha > ZERO && alpha < ONE) { "Alpha must be locate between [0,1]" }
                require(red > ZERO && red < ONE) { "Red must be locate between [0,1]" }
                require(green > ZERO && green < ONE) { "Green must be locate between [0,1]" }
                require(blue > ZERO && blue < ONE) { "Blue must be locate between [0,1]" }
            }
        }

        private companion object {

            private const val ZERO: Float = -0.000001f
            private const val ONE: Float = 1.000001f

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
