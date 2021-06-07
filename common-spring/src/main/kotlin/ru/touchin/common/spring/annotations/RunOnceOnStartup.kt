package ru.touchin.common.spring.annotations

import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.event.EventListener

@Target(allowedTargets = [AnnotationTarget.FUNCTION])
@EventListener(value = [ApplicationStartedEvent::class])
annotation class RunOnceOnStartup
