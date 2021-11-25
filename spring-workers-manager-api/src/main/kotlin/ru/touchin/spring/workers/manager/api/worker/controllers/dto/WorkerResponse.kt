package ru.touchin.spring.workers.manager.api.worker.controllers.dto

import ru.touchin.spring.workers.manager.core.worker.dto.Worker
import ru.touchin.spring.workers.manager.core.worker.enums.WorkerStatus
import java.time.ZonedDateTime

data class WorkerResponse(
    val workerName: String,
    val stoppedAt: ZonedDateTime?,
    val disabledAt: ZonedDateTime?,
    val status: WorkerStatus,
    val parallelExecutionEnabled: Boolean
)

fun Worker.toWorkerResponse() = WorkerResponse(
    workerName = this.name,
    stoppedAt = this.stoppedAt,
    disabledAt = this.disabledAt,
    status = this.status,
    parallelExecutionEnabled = this.parallelExecutionEnabled,
)
