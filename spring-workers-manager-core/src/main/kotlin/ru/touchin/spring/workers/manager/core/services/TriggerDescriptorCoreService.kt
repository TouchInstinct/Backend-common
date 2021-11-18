package ru.touchin.spring.workers.manager.core.services

import ru.touchin.spring.workers.manager.core.models.TriggerDescriptor

interface TriggerDescriptorCoreService {

    fun save(triggerDescriptor: TriggerDescriptor)
    fun delete(triggerDescriptor: TriggerDescriptor)
    fun getByWorkerName(workerName: String): List<TriggerDescriptor>

}
