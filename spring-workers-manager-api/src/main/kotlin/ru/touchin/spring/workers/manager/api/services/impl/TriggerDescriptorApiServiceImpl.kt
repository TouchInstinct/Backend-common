package ru.touchin.spring.workers.manager.api.services.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.spring.workers.manager.api.dto.TriggerChangeRequest
import ru.touchin.spring.workers.manager.api.dto.toModel
import ru.touchin.spring.workers.manager.api.services.TriggerDescriptorApiService
import ru.touchin.spring.workers.manager.core.models.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.models.Worker
import ru.touchin.spring.workers.manager.core.services.TriggerDescriptorCoreService
import ru.touchin.spring.workers.manager.core.services.WorkerCoreService

@Service
class TriggerDescriptorApiServiceImpl(
    private val workerCoreService: WorkerCoreService,
    private val triggerDescriptorCoreService: TriggerDescriptorCoreService
) : TriggerDescriptorApiService {

    @Transactional
    override fun create(workerName: String, body: TriggerChangeRequest) {
        val worker = workerCoreService.get(workerName)

        saveNewTrigger(body.toModel(), worker)
    }

    @Transactional
    override fun update(workerName: String, triggerName: String, body: TriggerChangeRequest) {
        val worker = workerCoreService.get(workerName)

        val triggerDescriptor = getTriggerDescriptor(worker, triggerName)

        triggerDescriptorCoreService.delete(triggerDescriptor)

        saveNewTrigger(body.toModel(), worker)
    }

    private fun getTriggerDescriptor(worker: Worker, triggerName: String): TriggerDescriptor {
        return worker
            .getAllTriggerDescriptors()
            .firstOrNull { it.triggerName == triggerName }
            ?: throw NoSuchElementException("Trigger not found by name $triggerName")
    }

    private fun saveNewTrigger(triggerDescriptor: TriggerDescriptor, worker: Worker) {
        triggerDescriptorCoreService.save(triggerDescriptor
            .also { it.worker = worker }
        )
    }

}
