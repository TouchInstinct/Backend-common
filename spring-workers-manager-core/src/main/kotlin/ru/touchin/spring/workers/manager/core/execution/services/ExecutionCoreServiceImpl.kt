package ru.touchin.spring.workers.manager.core.execution.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.spring.workers.manager.core.execution.converters.toExecution
import ru.touchin.spring.workers.manager.core.execution.dto.Execution
import ru.touchin.spring.workers.manager.core.execution.enums.ExecutionStatus
import ru.touchin.spring.workers.manager.core.execution.models.ExecutionEntity
import ru.touchin.spring.workers.manager.core.execution.repositories.ExecutionRepository
import ru.touchin.spring.workers.manager.core.execution.repositories.findByIdOrThrow
import ru.touchin.spring.workers.manager.core.execution.services.dto.CreateExecution
import ru.touchin.spring.workers.manager.core.trigger.repositories.TriggerDescriptorRepository
import ru.touchin.spring.workers.manager.core.trigger.repositories.findByIdOrThrow
import java.time.ZonedDateTime
import java.util.*

@Service
class ExecutionCoreServiceImpl(
    private val executionRepository: ExecutionRepository,
    private val triggerDescriptorRepository: TriggerDescriptorRepository,
) : ExecutionCoreService {

    @Transactional
    override fun create(create: CreateExecution): Execution {
        val entity = ExecutionEntity().apply {
            startedAt = ZonedDateTime.now()
            workerName = create.workerName
            triggerDescriptor = triggerDescriptorRepository.findByIdOrThrow(create.triggerId)
            status = ExecutionStatus.PROCESSING
        }

        return executionRepository.save(entity)
            .toExecution()
    }

    @Transactional
    override fun setFinished(id: UUID): Execution {
        val entity = executionRepository.findByIdOrThrow(id)

        entity.apply {
            finishedAt = ZonedDateTime.now()
            status = ExecutionStatus.FINISHED
        }

        return executionRepository.save(entity)
            .toExecution()
    }

}
