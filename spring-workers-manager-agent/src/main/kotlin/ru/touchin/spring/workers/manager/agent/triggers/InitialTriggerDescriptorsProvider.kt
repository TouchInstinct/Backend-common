package ru.touchin.spring.workers.manager.agent.triggers

import ru.touchin.spring.workers.manager.core.models.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.models.Worker

/**
 * When the agent is starting first time, then initial (default) triggers should be created for new workers.
 */
interface InitialTriggerDescriptorsProvider {

    fun applicableFor(worker: Worker): Boolean

    fun createInitialTriggerDescriptors(worker: Worker): List<TriggerDescriptor>

}
