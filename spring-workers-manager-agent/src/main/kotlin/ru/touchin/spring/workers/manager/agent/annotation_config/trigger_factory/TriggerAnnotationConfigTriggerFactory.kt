package ru.touchin.spring.workers.manager.agent.annotation_config.trigger_factory

import org.springframework.context.EmbeddedValueResolverAware
import org.springframework.stereotype.Component
import org.springframework.util.StringValueResolver
import ru.touchin.spring.workers.manager.agent.annotation_config.DefaultTrigger
import ru.touchin.spring.workers.manager.core.models.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.models.Worker
import ru.touchin.spring.workers.manager.core.models.enums.TriggerType
import java.lang.reflect.Method

/**
 * Creates triggers for methods annotated with [ru.touchin.spring.workers.manager.agent.annotation_config.DefaultTrigger] annotation
 */
@Component
class TriggerAnnotationConfigTriggerFactory
    : AnnotationConfigTriggerFactory,
    EmbeddedValueResolverAware {

    lateinit var valueResolver: StringValueResolver

    override fun setEmbeddedValueResolver(resolver: StringValueResolver) {
        valueResolver = resolver
    }

    override fun create(worker: Worker, actionMethod: Method): List<TriggerDescriptor> {
        val triggerAnnotation = actionMethod.getAnnotation(DefaultTrigger::class.java)
            ?: return emptyList()

        val trigger = TriggerDescriptor().also {
            it.worker = worker

            val resolvedType = valueResolver.resolveStringValue(triggerAnnotation.type)

            it.type = TriggerType.find(resolvedType)
                ?: throw IllegalArgumentException("Trigger type for name $resolvedType dies not exist")

            it.expression = valueResolver.resolveStringValue(triggerAnnotation.expression)
                ?: throw NullPointerException("Trigger for worker '${worker.workerName}' has null expression")

            it.triggerName = "${it.type.name}_${it.expression.replace(" ", "_")}"
        }

        return listOf(trigger)
    }

}
