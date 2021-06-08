package ru.touchin.common.geo

import org.springframework.context.annotation.Import
import ru.touchin.common.geo.configurations.GeoConfiguration

@Suppress("unused")
@Import(value = [GeoConfiguration::class])
annotation class EnableGeo
