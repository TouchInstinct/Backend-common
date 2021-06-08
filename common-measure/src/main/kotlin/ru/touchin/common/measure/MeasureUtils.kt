@file:Suppress("unused")

package ru.touchin.common.measure

import tech.units.indriya.unit.Units
import javax.measure.MetricPrefix
import javax.measure.Quantity
import javax.measure.Unit
import javax.measure.quantity.Length

object MeasureUtils {

    private val Kilometer: Unit<Length> = MetricPrefix.KILO(Units.METRE)

    fun Quantity<Length>.toKilometers(): Double {
        return to(Kilometer).value.toDouble()
    }

    fun Quantity<Length>.isMore(quantity: Quantity<Length>): Boolean {
        return this.subtract(quantity).value.toDouble() > 0
    }

    fun Quantity<Length>.isMoreOrEquals(quantity: Quantity<Length>): Boolean {
        return this.subtract(quantity).value.toDouble() >= 0
    }

    fun Quantity<Length>.isLess(quantity: Quantity<Length>): Boolean {
        return !isMoreOrEquals(quantity)
    }

    fun Quantity<Length>.isLessOrEquals(quantity: Quantity<Length>): Boolean {
        return !isMore(quantity)
    }

}
