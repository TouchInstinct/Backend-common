package ru.touchin.spring.workers.manager.core.trigger.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.spring.workers.manager.core.trigger.converters.toTriggerDescriptor
import ru.touchin.spring.workers.manager.core.trigger.dto.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.trigger.models.TriggerDescriptorEntity
import ru.touchin.spring.workers.manager.core.trigger.repositories.TriggerDescriptorRepository
import ru.touchin.spring.workers.manager.core.trigger.repositories.findByIdOrThrow
import ru.touchin.spring.workers.manager.core.trigger.services.dto.CreateTriggerDescriptor
import ru.touchin.spring.workers.manager.core.worker.repositories.WorkerRepository
import ru.touchin.spring.workers.manager.core.worker.repositories.findByNameOrThrow
import java.time.ZonedDateTime
import java.util.*

@Service
class TriggerDescriptorCoreServiceImpl(
    private val triggerDescriptorRepository: TriggerDescriptorRepository,
    private val workerRepository: WorkerRepository,
) : TriggerDescriptorCoreService {

    @Transactional
    override fun create(create: CreateTriggerDescriptor): TriggerDescriptor {
        val entity = TriggerDescriptorEntity().apply {
            expression = create.expression
            triggerName = create.name
            type = create.type
            worker = workerRepository.findByNameOrThrow(create.workerName)
            disabledAt = create.disabledAt
        }

        return triggerDescriptorRepository.save(entity)
            .toTriggerDescriptor()
    }

    @Transactional
    override fun create(create: List<CreateTriggerDescriptor>): List<TriggerDescriptor> {
        val entities = create.map { dto ->
            TriggerDescriptorEntity().apply {
                expression = dto.expression
                triggerName = dto.name
                type = dto.type
                worker = workerRepository.findByNameOrThrow(dto.workerName)
                disabledAt = dto.disabledAt
            }
        }

        return triggerDescriptorRepository.saveAll(entities)
            .map(TriggerDescriptorEntity::toTriggerDescriptor)
    }

    @Transactional
    override fun setDeleted(id: UUID): TriggerDescriptor {
        val entity = triggerDescriptorRepository.findByIdOrThrow(id)

        entity.apply { deletedAt = ZonedDateTime.now() }

        return triggerDescriptorRepository.save(entity)
            .toTriggerDescriptor()
    }

    @Transactional(readOnly = true)
    override fun getByWorkerName(workerName: String): List<TriggerDescriptor> {
        return triggerDescriptorRepository.findAll(workerName)
            .map(TriggerDescriptorEntity::toTriggerDescriptor)
    }

}
