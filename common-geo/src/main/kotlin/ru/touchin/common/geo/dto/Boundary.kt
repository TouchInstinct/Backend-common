package ru.touchin.common.geo.dto

import java.math.BigDecimal

data class Boundary(
    val minLat: BigDecimal,
    val minLon: BigDecimal,
    val maxLat: BigDecimal,
    val maxLon: BigDecimal
)
