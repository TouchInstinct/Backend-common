package ru.touchin.spring.workers.manager.core.execution.converters

import ru.touchin.spring.workers.manager.core.execution.dto.Execution
import ru.touchin.spring.workers.manager.core.execution.models.ExecutionEntity
import ru.touchin.spring.workers.manager.core.trigger.converters.toTriggerDescriptor

fun ExecutionEntity.toExecution(): Execution {
    return Execution(
        id = id!!,
        triggerDescriptor = triggerDescriptor?.toTriggerDescriptor(),
        status = status,
        startedAt = startedAt,
        finishedAt = finishedAt,
    )
}
