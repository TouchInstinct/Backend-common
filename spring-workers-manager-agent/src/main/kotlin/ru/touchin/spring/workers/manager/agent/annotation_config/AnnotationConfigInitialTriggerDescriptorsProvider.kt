package ru.touchin.spring.workers.manager.agent.annotation_config

import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import ru.touchin.spring.workers.manager.agent.annotation_config.trigger_factory.AnnotationConfigTriggerFactory
import ru.touchin.spring.workers.manager.agent.trigger.InitialTriggerDescriptorsProvider
import ru.touchin.spring.workers.manager.core.trigger.dto.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.trigger.models.TriggerDescriptorEntity
import ru.touchin.spring.workers.manager.core.trigger.services.dto.CreateTriggerDescriptor
import ru.touchin.spring.workers.manager.core.worker.dto.Worker
import ru.touchin.spring.workers.manager.core.worker.models.WorkerEntity

@Component
class AnnotationConfigInitialTriggerDescriptorsProvider(
    private val triggersCollector: AnnotationConfigCollectingBeanPostProcessor,
    private val triggerFactories: List<AnnotationConfigTriggerFactory>
) : InitialTriggerDescriptorsProvider {

    val jobName2Triggers: MultiValueMap<String, CreateTriggerDescriptor> = LinkedMultiValueMap()

    override fun applicableFor(worker: Worker): Boolean {
        val actionMethod = triggersCollector.jobName2Method[worker.name]
            ?: return false

        val triggers = triggerFactories.flatMap { it.create(worker, actionMethod) }

        if (triggers.isEmpty()) {
            return false
        }

        jobName2Triggers.addAll(worker.name, triggers)

        return true
    }

    override fun createInitialTriggerDescriptors(worker: Worker): List<CreateTriggerDescriptor> {
        return jobName2Triggers[worker.name].orEmpty()
    }

}
