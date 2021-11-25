package ru.touchin.spring.workers.manager.core.execution.dto

import ru.touchin.spring.workers.manager.core.execution.enums.ExecutionStatus
import ru.touchin.spring.workers.manager.core.trigger.dto.TriggerDescriptor
import java.time.ZonedDateTime
import java.util.*

data class Execution(
    val id: UUID,
    val triggerDescriptor: TriggerDescriptor?,
    val status: ExecutionStatus,
    val startedAt: ZonedDateTime?,
    val finishedAt: ZonedDateTime?,
)
