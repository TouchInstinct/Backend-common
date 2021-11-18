package ru.touchin.spring.workers.manager.agent.triggers

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import ru.touchin.spring.workers.manager.core.models.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.models.Worker

/**
 * When no triggers could be created by any provider, then no triggers created.
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
class NoInitialTriggersDescriptorsProvider : InitialTriggerDescriptorsProvider {

    override fun applicableFor(worker: Worker): Boolean = true

    override fun createInitialTriggerDescriptors(worker: Worker): List<TriggerDescriptor> = emptyList()

}
