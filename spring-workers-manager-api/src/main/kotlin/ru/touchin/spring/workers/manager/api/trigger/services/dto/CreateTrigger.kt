package ru.touchin.spring.workers.manager.api.trigger.services.dto

import ru.touchin.spring.workers.manager.core.trigger.enums.TriggerType

data class CreateTrigger(
    val workerName: String,
    val triggerName: String,
    val type: TriggerType,
    val expression: String,
    val disabled: Boolean,
)
