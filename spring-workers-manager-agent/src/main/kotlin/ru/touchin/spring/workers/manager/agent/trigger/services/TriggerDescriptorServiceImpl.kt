package ru.touchin.spring.workers.manager.agent.trigger.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.spring.workers.manager.agent.trigger.InitialTriggerDescriptorsProvider
import ru.touchin.spring.workers.manager.core.trigger.dto.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.trigger.services.TriggerDescriptorCoreService
import ru.touchin.spring.workers.manager.core.worker.dto.Worker

@Service
class TriggerDescriptorServiceImpl(
    private val triggerDescriptorCoreService: TriggerDescriptorCoreService,
    private val initialTriggerDescriptorProviders: List<InitialTriggerDescriptorsProvider>,
) : TriggerDescriptorService {

    @Transactional
    override fun createDefaultTriggerDescriptors(worker: Worker): List<TriggerDescriptor> {
        return triggerDescriptorCoreService.create(
            initialTriggerDescriptorProviders
                .filter { it.applicableFor(worker) }
                .flatMap { it.createInitialTriggerDescriptors(worker) }
        )
    }

}
