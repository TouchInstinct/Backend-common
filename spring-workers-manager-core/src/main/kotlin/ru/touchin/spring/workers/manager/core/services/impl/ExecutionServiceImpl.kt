package ru.touchin.spring.workers.manager.core.services.impl

import org.springframework.stereotype.Service
import ru.touchin.spring.workers.manager.core.models.Execution
import ru.touchin.spring.workers.manager.core.models.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.models.Worker
import ru.touchin.spring.workers.manager.core.models.enums.ExecutionStatus
import ru.touchin.spring.workers.manager.core.repositories.ExecutionRepository
import ru.touchin.spring.workers.manager.core.repositories.upsert
import ru.touchin.spring.workers.manager.core.services.ExecutionService
import java.time.ZonedDateTime

@Service
class ExecutionServiceImpl(
    private val executionRepository: ExecutionRepository
) : ExecutionService {

    override fun createExecution(worker: Worker, triggerDescriptor: TriggerDescriptor?): Execution {
        return executionRepository.upsert(Execution()) {
            it.startedAt = ZonedDateTime.now()
            it.workerName = worker.workerName
            it.triggerDescriptor = triggerDescriptor
            it.status = ExecutionStatus.PROCESSING
        }
    }

    override fun finishExecution(execution: Execution) {
        executionRepository.upsert(execution) {
            it.finishedAt = ZonedDateTime.now()
            it.status = ExecutionStatus.FINISHED
        }
    }

}
