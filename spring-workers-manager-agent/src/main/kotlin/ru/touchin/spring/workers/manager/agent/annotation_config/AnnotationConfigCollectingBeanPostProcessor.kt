package ru.touchin.spring.workers.manager.agent.annotation_config

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import ru.touchin.spring.workers.manager.agent.annotation_config.job_factory.AnnotationConfigJobFactory
import ru.touchin.spring.workers.manager.agent.common.base.BaseJob
import java.lang.reflect.Method

/**
 * 1. Scans components for [ScheduledAction] annotation
 * 2. Keeps metadata of that components
 * 3. Creates [BaseJob] for methods created
 */
@Component
class AnnotationConfigCollectingBeanPostProcessor(
    private val jobFactories: List<AnnotationConfigJobFactory>
) : BeanPostProcessor {

    val jobs: MutableList<BaseJob> = ArrayList()

    val jobName2Method: MutableMap<String, Method> = HashMap()

    /**
     * Bean name -> class of this bean.
     *
     * Contains only entries for classes with [ScheduledAction] annotation.
     */
    private val beanName2OriginalClass: MutableMap<String, Class<*>> = HashMap()

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        val hasMethodsForScheduling = bean.javaClass.declaredMethods
            .any { it.isAnnotationPresent(ScheduledAction::class.java) }

        if (hasMethodsForScheduling) {
            beanName2OriginalClass[beanName] = bean.javaClass
        }

        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        val clazz = beanName2OriginalClass[beanName]
            ?: return bean

        val actionMethod = findActionMethod(clazz)

        val createdJobs = jobFactories.flatMap { it.create(bean, actionMethod) }

        createdJobs.forEach {
            jobName2Method[it.getName()] = actionMethod
        }

        jobs.addAll(createdJobs)

        return bean
    }

    companion object {

        private fun findActionMethod(clazz: Class<*>): Method {
            return clazz.declaredMethods
                .filter { it.isAnnotationPresent(ScheduledAction::class.java) }
                .also { annotatedMethods ->
                    check(annotatedMethods.size <= 1) {
                        "Class `${clazz.name}` has more that one methods with annotation @Scheduled. " +
                            "Methods: $annotatedMethods"
                    }
                }
                .onEach { annotatedMethod ->
                    check(annotatedMethod.parameters.isEmpty()) {
                        "Method ${clazz.name}:${annotatedMethod.name}' must not have arguments for scheduling, " +
                            "but requires ${annotatedMethod.parameters.size} parameters"
                    }
                }
                .single()
        }

    }

}
