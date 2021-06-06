package ru.touchin.common.spring.jpa

import org.springframework.context.annotation.Import
import ru.touchin.common.spring.jpa.configurations.AuditingConfig

@Suppress("unused")
@Import(value = [AuditingConfig::class])
annotation class EnableJpaAuditingExtra
