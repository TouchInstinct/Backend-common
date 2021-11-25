package ru.touchin.spring.workers.manager.core.trigger.converters

import ru.touchin.spring.workers.manager.core.trigger.dto.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.trigger.models.TriggerDescriptorEntity

fun TriggerDescriptorEntity.toTriggerDescriptor(): TriggerDescriptor {
    return TriggerDescriptor(
        id = id!!,
        name = triggerName,
        type = type,
        expression = expression,
        workerName = worker.workerName,
        disabledAt = disabledAt,
        deletedAt = deletedAt,
    )
}
