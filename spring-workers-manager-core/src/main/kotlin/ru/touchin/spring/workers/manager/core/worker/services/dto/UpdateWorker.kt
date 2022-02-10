package ru.touchin.spring.workers.manager.core.worker.services.dto

import ru.touchin.spring.workers.manager.core.worker.enums.WorkerStatus

data class UpdateWorker(
    val name: String,
    val status: WorkerStatus,
)
