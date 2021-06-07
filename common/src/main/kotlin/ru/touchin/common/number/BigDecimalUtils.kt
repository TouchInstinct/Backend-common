@file:Suppress("unused")
package ru.touchin.common.number

import java.math.BigDecimal

object BigDecimalUtils {

    fun BigDecimal.equalTo(number: BigDecimal): Boolean = compareTo(number) == 0

    fun <T> Iterable<T>.sumBy(selector: (T) -> BigDecimal): BigDecimal =
        fold(BigDecimal.ZERO) { accumulator, element -> accumulator + selector(element) }

}
