package ru.touchin.spring.workers.manager.core.execution.services.dto

import java.util.*

data class CreateExecution(
    val workerName: String,
    val triggerId: UUID,
)
