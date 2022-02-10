package ru.touchin.spring.workers.manager.agent.annotation_config.trigger_factory

import org.springframework.context.EmbeddedValueResolverAware
import org.springframework.stereotype.Component
import org.springframework.util.StringValueResolver
import ru.touchin.spring.workers.manager.agent.annotation_config.InitTrigger
import ru.touchin.spring.workers.manager.core.trigger.enums.TriggerType
import ru.touchin.spring.workers.manager.core.trigger.services.dto.CreateTriggerDescriptor
import ru.touchin.spring.workers.manager.core.worker.dto.Worker
import java.lang.reflect.Method

/**
 * Creates triggers for methods annotated with [ru.touchin.spring.workers.manager.agent.annotation_config.InitTrigger] annotation
 */
@Component
class TriggerAnnotationConfigTriggerFactory
    : AnnotationConfigTriggerFactory,
    EmbeddedValueResolverAware {

    lateinit var valueResolver: StringValueResolver

    override fun setEmbeddedValueResolver(resolver: StringValueResolver) {
        valueResolver = resolver
    }

    override fun create(worker: Worker, actionMethod: Method): List<CreateTriggerDescriptor> {
        val triggerAnnotation = actionMethod.getAnnotation(InitTrigger::class.java)
            ?: return emptyList()

        val resolvedType = valueResolver.resolveStringValue(triggerAnnotation.type)
        val triggerType = TriggerType.find(resolvedType)
            ?: throw IllegalArgumentException("Trigger type for name $resolvedType dies not exist")

        val expression = valueResolver.resolveStringValue(triggerAnnotation.expression)
            ?: throw NullPointerException("Trigger for worker '${worker.name}' has null expression")

        val trigger = CreateTriggerDescriptor(
            name = "${triggerType.name}_${expression.replace(" ", "_")}",
            type = triggerType,
            workerName = worker.name,
            expression = valueResolver.resolveStringValue(triggerAnnotation.expression)
                ?: throw NullPointerException("Trigger for worker '${worker.name}' has null expression"),
            disabledAt = null,
        )

        return listOf(trigger)
    }

}
