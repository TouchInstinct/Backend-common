package ru.touchin.spring.workers.manager.agent.config

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.touchin.spring.workers.manager.agent.trigger.services.TriggerDescriptorService
import ru.touchin.spring.workers.manager.agent.registry.JobDefinitionsRegistry
import ru.touchin.spring.workers.manager.core.worker.services.WorkerCoreService
import ru.touchin.spring.workers.manager.core.worker.services.WorkersStateService

@Component
class WorkerInitializer(
    private val triggerDescriptorAgentService: TriggerDescriptorService,
    private val jobDefinitionsRegistry: JobDefinitionsRegistry,
    private val workerCoreService: WorkerCoreService,
    private val workerStateService: WorkersStateService,
) {

    @Transactional
    fun init() {
        initWorkers(jobDefinitionsRegistry.jobNames)
    }

    private fun initWorkers(jobNames: Set<String>) {
        jobNames.forEach(this::getOrCreateWorkerWithTriggers)
    }

    private fun getOrCreateWorkerWithTriggers(name: String) {
        workerCoreService.getWithLock(name)
            ?.let { workerStateService.start(it.name) }
            ?: createWorkerWithTriggers(name)
    }

    private fun createWorkerWithTriggers(name: String) {
        val worker = workerCoreService.create(name)

        triggerDescriptorAgentService.createDefaultTriggerDescriptors(worker)
    }

}
