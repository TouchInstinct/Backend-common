package ru.touchin.spring.workers.manager.agent.config

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.touchin.spring.workers.manager.agent.services.TriggerDescriptorService
import ru.touchin.spring.workers.manager.agent.services.WorkerService
import ru.touchin.spring.workers.manager.agent.registry.JobDefinitionsRegistry

@Component
class WorkerInitializer(
    private val triggerDescriptorAgentService: TriggerDescriptorService,
    private val jobDefinitionsRegistry: JobDefinitionsRegistry,
    private val workerService: WorkerService
) {

    @Transactional
    fun init() {
        initWorkers(jobDefinitionsRegistry.jobNames)
    }

    private fun initWorkers(jobNames: Set<String>) {
        jobNames.forEach(this::getOrCreateWorkerWithTriggers)
    }

    private fun getOrCreateWorkerWithTriggers(name: String) {
        workerService.getWithLock(name)
            ?.let(workerService::unsetStopped)
            ?: createWorkerWithTriggers(name)
    }

    private fun createWorkerWithTriggers(name: String) {
        val worker = workerService.create(name)

        triggerDescriptorAgentService.createDefaultTriggerDescriptors(worker)
    }

}
