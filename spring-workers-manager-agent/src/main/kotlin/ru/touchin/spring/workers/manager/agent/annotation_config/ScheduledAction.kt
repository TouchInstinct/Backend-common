package ru.touchin.spring.workers.manager.agent.annotation_config

@Target(AnnotationTarget.FUNCTION)
annotation class ScheduledAction(
    /**
     * Job name. Defaults to class name.
     */
    val name: String = ""
)

