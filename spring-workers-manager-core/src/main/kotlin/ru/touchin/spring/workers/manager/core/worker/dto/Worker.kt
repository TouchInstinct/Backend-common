package ru.touchin.spring.workers.manager.core.worker.dto

import ru.touchin.spring.workers.manager.core.trigger.dto.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.worker.enums.WorkerStatus
import java.time.ZonedDateTime

data class Worker(
    val name: String,
    val stoppedAt: ZonedDateTime?,
    val disabledAt: ZonedDateTime?,
    val status: WorkerStatus,
    val parallelExecutionEnabled: Boolean,
) {

    fun isStopped() = stoppedAt != null

    fun isDisabled() = disabledAt != null

}
