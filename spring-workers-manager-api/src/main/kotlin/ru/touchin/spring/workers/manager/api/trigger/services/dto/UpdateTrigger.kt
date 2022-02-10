package ru.touchin.spring.workers.manager.api.trigger.services.dto

import ru.touchin.spring.workers.manager.core.trigger.enums.TriggerType

data class UpdateTrigger(
    val workerName: String,
    val oldTriggerName: String,
    val newTriggerName: String,
    val type: TriggerType,
    val expression: String,
    val disabled: Boolean,
)
