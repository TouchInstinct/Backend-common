package ru.touchin.spring.workers.manager.core.trigger.services.dto

import ru.touchin.spring.workers.manager.core.trigger.enums.TriggerType
import java.time.ZonedDateTime

data class CreateTriggerDescriptor(
    val name: String,
    val type: TriggerType,
    val expression: String,
    val disabledAt: ZonedDateTime?,
    val workerName: String,
)
