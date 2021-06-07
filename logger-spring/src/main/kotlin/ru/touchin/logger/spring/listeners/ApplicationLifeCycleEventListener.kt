@file:Suppress("unused")
package ru.touchin.logger.spring.listeners

import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.touchin.logger.factory.LogBuilderFactory
import ru.touchin.logger.dto.LogData
import ru.touchin.logger.dto.LogDuration

private const val APPLICATION = "application"

@Component
class ApplicationLifeCycleEventListener(
    private val logBuilderFactory: LogBuilderFactory<LogData>
) {
    lateinit var duration: LogDuration

    @EventListener(ApplicationStartedEvent::class)
    fun onApplicationStart() {
        duration = LogDuration()

        logBuilderFactory.create(this::class.java)
            .addTags(APPLICATION, "started")
            .build()
            .log()
    }

    @EventListener(ContextClosedEvent::class)
    fun onApplicationStopped() {
        logBuilderFactory.create(this::class.java)
            .addTags(APPLICATION, "stopped")
            .setDuration(duration)
            .build()
            .log()
    }

}
