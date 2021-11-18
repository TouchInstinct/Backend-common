package ru.touchin.spring.workers.manager.api.dto

import ru.touchin.spring.workers.manager.core.models.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.models.enums.TriggerType
import java.time.ZonedDateTime

data class TriggerChangeRequest(
    val name: String,
    val type: TriggerType,
    val expression: String,
    val disabled: Boolean
)

fun TriggerChangeRequest.toModel(newTriggerDescriptor: TriggerDescriptor? = null): TriggerDescriptor {
    return (newTriggerDescriptor ?: TriggerDescriptor()).also { descriptor ->
        descriptor.expression = this.expression
        descriptor.triggerName = this.name
        descriptor.type = this.type
        descriptor.disabledAt = if (this.disabled) descriptor.disabledAt ?: ZonedDateTime.now() else null
    }
}
