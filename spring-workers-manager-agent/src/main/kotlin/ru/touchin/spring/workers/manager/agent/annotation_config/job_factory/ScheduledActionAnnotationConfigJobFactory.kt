package ru.touchin.spring.workers.manager.agent.annotation_config.job_factory

import org.springframework.stereotype.Component
import ru.touchin.spring.workers.manager.agent.annotation_config.ScheduledAction
import ru.touchin.spring.workers.manager.agent.base.BaseJob
import java.lang.reflect.Method

/**
 * Creates job instances for every annotated action method.
 */
@Component
class ScheduledActionAnnotationConfigJobFactory
    : AnnotationConfigJobFactory {

    override fun create(bean: Any, actionMethod: Method): List<BaseJob> {
        val job = createJobForBean(bean, actionMethod)

        return listOf(job)
    }

    companion object {

        private fun createJobForBean(bean: Any, annotatedMethod: Method): BaseJob {
            val targetMethod = bean.javaClass.getMethod(annotatedMethod)
            val annotation = annotatedMethod.getAnnotation(ScheduledAction::class.java)

            val jobName: String = annotation.name.takeIf { it.isNotBlank() }
                ?: annotatedMethod.declaringClass.name

            return createJob(jobName) { targetMethod.invoke(bean) }
        }

        private fun Class<*>.getMethod(sampleMethod: Method): Method {
            return getMethod(sampleMethod.name, *sampleMethod.parameterTypes)
                .apply { isAccessible = true }
        }

        private fun createJob(jobName: String, func: () -> Unit): BaseJob = object : BaseJob {

            override fun getName() = jobName

            override fun run() {
                func.invoke()
            }

        }

    }

}

