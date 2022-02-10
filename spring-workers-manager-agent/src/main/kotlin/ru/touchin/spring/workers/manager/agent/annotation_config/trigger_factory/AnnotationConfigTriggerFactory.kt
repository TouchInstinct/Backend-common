package ru.touchin.spring.workers.manager.agent.annotation_config.trigger_factory

import ru.touchin.spring.workers.manager.core.trigger.services.dto.CreateTriggerDescriptor
import ru.touchin.spring.workers.manager.core.worker.dto.Worker
import java.lang.reflect.Method

/**
 * Used to create initial triggers for new workers
 */
interface AnnotationConfigTriggerFactory {

    fun create(worker: Worker, actionMethod: Method): List<CreateTriggerDescriptor>

}
