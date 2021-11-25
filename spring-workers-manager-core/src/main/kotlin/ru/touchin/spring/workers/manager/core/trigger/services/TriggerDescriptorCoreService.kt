package ru.touchin.spring.workers.manager.core.trigger.services

import ru.touchin.spring.workers.manager.core.trigger.dto.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.trigger.services.dto.CreateTriggerDescriptor
import java.util.*

interface TriggerDescriptorCoreService {

    fun create(create: CreateTriggerDescriptor): TriggerDescriptor
    fun create(create: List<CreateTriggerDescriptor>): List<TriggerDescriptor>
    fun setDeleted(id: UUID): TriggerDescriptor
    fun getByWorkerName(workerName: String): List<TriggerDescriptor>

}
