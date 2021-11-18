package ru.touchin.spring.workers.manager.agent.services

import ru.touchin.spring.workers.manager.core.models.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.models.Worker
import java.time.ZonedDateTime

interface TriggerDescriptorService {

    fun createDefaultTriggerDescriptors(worker: Worker): List<TriggerDescriptor>
    fun setTriggerDescriptorDisable(triggerDescriptor: TriggerDescriptor, disabledAt: ZonedDateTime?)

}
