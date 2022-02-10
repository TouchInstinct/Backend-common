package ru.touchin.spring.workers.manager.core.worker.converters

import ru.touchin.spring.workers.manager.core.trigger.converters.toTriggerDescriptor
import ru.touchin.spring.workers.manager.core.trigger.models.TriggerDescriptorEntity
import ru.touchin.spring.workers.manager.core.worker.dto.Worker
import ru.touchin.spring.workers.manager.core.worker.models.WorkerEntity

fun WorkerEntity.toWorker(): Worker {
    return Worker(
        name = workerName,
        stoppedAt = stoppedAt,
        disabledAt = disabledAt,
        status = status,
        parallelExecutionEnabled = parallelExecutionEnabled,
    )
}
