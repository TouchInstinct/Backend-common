package ru.touchin.spring.workers.manager.agent.annotation_config

import java.lang.annotation.Inherited

/**
 * Adds default trigger to [ScheduledAction].
 * Default trigger is submitted if job is launched first time.
 */
@Inherited
@Target(AnnotationTarget.FUNCTION)
annotation class DefaultTrigger(
    val name: String = "",
    val type: String,
    val expression: String
)
