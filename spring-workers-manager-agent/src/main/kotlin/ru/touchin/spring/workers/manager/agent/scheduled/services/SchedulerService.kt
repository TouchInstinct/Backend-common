package ru.touchin.spring.workers.manager.agent.scheduled.services

import ru.touchin.spring.workers.manager.core.trigger.dto.TriggerDescriptor

interface SchedulerService {

    fun scheduleTriggers(triggerDescriptors: List<TriggerDescriptor>)
    fun unscheduleTriggers(triggerDescriptors: List<TriggerDescriptor>)

}
