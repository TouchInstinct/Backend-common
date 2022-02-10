package ru.touchin.spring.workers.manager.api.trigger.controllers.dto

import ru.touchin.spring.workers.manager.core.trigger.enums.TriggerType

data class TriggerChangeRequest(
    val name: String,
    val type: TriggerType,
    val expression: String,
    val disabled: Boolean,
)
