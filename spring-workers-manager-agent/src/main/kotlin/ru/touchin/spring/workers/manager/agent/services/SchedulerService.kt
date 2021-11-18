package ru.touchin.spring.workers.manager.agent.services

import ru.touchin.spring.workers.manager.core.models.TriggerDescriptor

interface SchedulerService {

    fun scheduleTriggers(triggerDescriptors: List<TriggerDescriptor>)
    fun unscheduleTriggers(triggerDescriptors: List<TriggerDescriptor>)

}
