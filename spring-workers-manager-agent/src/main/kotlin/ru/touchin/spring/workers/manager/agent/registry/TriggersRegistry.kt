package ru.touchin.spring.workers.manager.agent.registry

import org.quartz.Trigger
import org.springframework.stereotype.Component
import ru.touchin.spring.workers.manager.core.models.TriggerDescriptor
import java.util.concurrent.ConcurrentHashMap

@Component
class TriggersRegistry {

    // Concurrent impl to prevent java.util.ConcurrentModificationException
    private val descriptors2triggers: MutableMap<TriggerDescriptor, Trigger> = ConcurrentHashMap()

    fun getTriggerByDescriptor(descriptor: TriggerDescriptor): Trigger? {
        return descriptors2triggers[descriptor]
    }

    fun getDescriptorByTrigger(trigger: Trigger): TriggerDescriptor? {
        return descriptors2triggers
            .entries
            .firstOrNull { it.value == trigger }
            ?.key
    }

    fun getDescriptors(): List<TriggerDescriptor> {
        return descriptors2triggers.keys.toList()
    }

    fun putTrigger(descriptor: TriggerDescriptor, trigger: Trigger) {
        descriptors2triggers[descriptor] = trigger
    }

    fun remove(triggerDescriptors: List<TriggerDescriptor>) {
        return triggerDescriptors.forEach { descriptors2triggers.remove(it) }
    }

}
