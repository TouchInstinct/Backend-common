package ru.touchin.spring.workers.manager.agent

import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import ru.touchin.common.spring.Ordered
import ru.touchin.spring.workers.manager.agent.config.WorkerInitializer
import ru.touchin.spring.workers.manager.agent.scheduled.WorkerManagerWatcher
import ru.touchin.spring.workers.manager.core.config.LiquibaseRunner

/**
 * Prepares required resources and initializes agent.
 */
@Component
class AgentInitializer(
    private val liquibase: LiquibaseRunner,
    private val workerInitializer: WorkerInitializer,
    private val workerWatcher: WorkerManagerWatcher
) {

    @EventListener(value = [ApplicationStartedEvent::class])
    @Order(Ordered.HIGH)
    fun execute() {
        liquibase.run()
        workerInitializer.init()
        workerWatcher.init()
    }

}
