package ru.touchin.spring.workers.manager.api.trigger.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.spring.workers.manager.api.trigger.services.dto.CreateTrigger
import ru.touchin.spring.workers.manager.api.trigger.services.dto.UpdateTrigger
import ru.touchin.spring.workers.manager.core.trigger.dto.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.trigger.exceptions.TriggerNotFoundException
import ru.touchin.spring.workers.manager.core.trigger.services.TriggerDescriptorCoreService
import ru.touchin.spring.workers.manager.core.trigger.services.dto.CreateTriggerDescriptor
import ru.touchin.spring.workers.manager.core.worker.dto.Worker
import ru.touchin.spring.workers.manager.core.worker.services.WorkerCoreService
import java.time.ZonedDateTime

@Service
class TriggerDescriptorApiServiceImpl(
    private val workerCoreService: WorkerCoreService,
    private val triggerDescriptorCoreService: TriggerDescriptorCoreService,
) : TriggerDescriptorApiService {

    @Transactional
    override fun create(create: CreateTrigger) {
        val worker = workerCoreService.get(create.workerName)

        triggerDescriptorCoreService.create(
            CreateTriggerDescriptor(
                expression = create.expression,
                name = create.triggerName,
                type = create.type,
                disabledAt = if (create.disabled) ZonedDateTime.now() else null,
                workerName = worker.name,
            )
        )
    }

    @Transactional
    override fun update(update: UpdateTrigger) {
        val worker = workerCoreService.get(update.workerName)

        val triggerDescriptor = getTriggerDescriptor(worker, update.oldTriggerName)

        triggerDescriptorCoreService.setDeleted(triggerDescriptor.id)

        triggerDescriptorCoreService.create(
            CreateTriggerDescriptor(
                expression = update.expression,
                name = update.newTriggerName,
                type = update.type,
                disabledAt = if (update.disabled) ZonedDateTime.now() else null,
                workerName = worker.name,
            )
        )
    }

    private fun getTriggerDescriptor(worker: Worker, triggerName: String): TriggerDescriptor {
        return triggerDescriptorCoreService.getByWorkerName(worker.name)
            .firstOrNull { it.name == triggerName }
            ?: throw TriggerNotFoundException(triggerName)
    }

}
