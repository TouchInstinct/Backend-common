package ru.touchin.spring.workers.manager.api.dto

import ru.touchin.spring.workers.manager.core.models.Worker
import ru.touchin.spring.workers.manager.core.models.enums.WorkerStatus
import java.time.ZonedDateTime

data class WorkerDto(
    val workerName: String,
    val stoppedAt: ZonedDateTime?,
    val disabledAt: ZonedDateTime?,
    val status: WorkerStatus,
    val parallelExecutionEnabled: Boolean
)

fun Worker.toDto() = WorkerDto(
    workerName = this.workerName,
    stoppedAt = this.stoppedAt,
    disabledAt = this.disabledAt,
    status = this.status,
    parallelExecutionEnabled = this.parallelExecutionEnabled
)
