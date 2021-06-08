package ru.touchin.common.geo

import org.springframework.context.annotation.Import
import ru.touchin.common.geo.configurations.GeoHelperConfiguration

@Suppress("unused")
@Import(value = [GeoHelperConfiguration::class])
annotation class EnableGeoHelper
