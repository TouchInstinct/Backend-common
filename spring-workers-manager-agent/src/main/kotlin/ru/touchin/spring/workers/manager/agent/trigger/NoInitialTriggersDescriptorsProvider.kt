package ru.touchin.spring.workers.manager.agent.trigger

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import ru.touchin.common.spring.Ordered
import ru.touchin.spring.workers.manager.core.trigger.services.dto.CreateTriggerDescriptor
import ru.touchin.spring.workers.manager.core.worker.dto.Worker

/**
 * When no triggers could be created by any provider, then no triggers created.
 */
@Component
@Order(Ordered.LOWER)
class NoInitialTriggersDescriptorsProvider : InitialTriggerDescriptorsProvider {

    override fun applicableFor(worker: Worker): Boolean = true

    override fun createInitialTriggerDescriptors(worker: Worker): List<CreateTriggerDescriptor> = emptyList()

}
