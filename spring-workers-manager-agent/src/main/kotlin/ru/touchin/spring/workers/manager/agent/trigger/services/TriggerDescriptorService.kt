package ru.touchin.spring.workers.manager.agent.trigger.services

import ru.touchin.spring.workers.manager.core.trigger.dto.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.trigger.models.TriggerDescriptorEntity
import ru.touchin.spring.workers.manager.core.worker.dto.Worker
import ru.touchin.spring.workers.manager.core.worker.models.WorkerEntity
import java.time.ZonedDateTime

interface TriggerDescriptorService {

    fun createDefaultTriggerDescriptors(worker: Worker): List<TriggerDescriptor>

}
