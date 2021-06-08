package ru.touchin.common.measure

import org.springframework.context.annotation.Import
import ru.touchin.common.measure.configurations.MeasureConfiguration

@Suppress("unused")
@Import(value = [MeasureConfiguration::class])
annotation class EnableTouchinMeasure
