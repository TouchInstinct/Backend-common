package ru.touchin.spring.workers.manager.agent.annotation_config.job_factory

import ru.touchin.spring.workers.manager.agent.base.BaseJob
import java.lang.reflect.Method

/**
 * Invoked when found method with [ScheduledAction] annotation in some bean.
 *
 * Used to read jobs settings from annotations in components.
 */
interface AnnotationConfigJobFactory {

    /**
     * Warning: As Spring could substitute actual beans with proxy-objects,
     * you must carefully check if [actionMethod] is applicable for [bean].
     */
    fun create(bean: Any, actionMethod: Method): List<BaseJob>

}
