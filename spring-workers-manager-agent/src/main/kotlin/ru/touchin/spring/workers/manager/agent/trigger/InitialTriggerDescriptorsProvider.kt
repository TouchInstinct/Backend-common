package ru.touchin.spring.workers.manager.agent.trigger

import ru.touchin.spring.workers.manager.core.trigger.services.dto.CreateTriggerDescriptor
import ru.touchin.spring.workers.manager.core.worker.dto.Worker

/**
 * When the agent is starting first time, then initial (default) triggers should be created for new workers.
 */
interface InitialTriggerDescriptorsProvider {

    fun applicableFor(worker: Worker): Boolean
    fun createInitialTriggerDescriptors(worker: Worker): List<CreateTriggerDescriptor>

}
