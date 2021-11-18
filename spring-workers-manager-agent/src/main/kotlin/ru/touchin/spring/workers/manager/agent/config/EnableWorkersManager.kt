package ru.touchin.spring.workers.manager.agent.config

import org.springframework.context.annotation.Import
import ru.touchin.spring.workers.manager.WorkersManagerConfiguration

/**
 * Annotation to enable Workers Manager module in Spring components via annotations.
 */
@Import(WorkersManagerConfiguration::class)
annotation class EnableWorkersManager
