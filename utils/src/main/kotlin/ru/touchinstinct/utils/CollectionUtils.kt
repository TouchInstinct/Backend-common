package ru.touchinstinct.utils

import java.math.BigDecimal

inline fun <T> Iterable<T>.sumBy(selector: (T) -> BigDecimal) =
    fold(BigDecimal.ZERO) { accumulator, element -> accumulator + selector(element) }
