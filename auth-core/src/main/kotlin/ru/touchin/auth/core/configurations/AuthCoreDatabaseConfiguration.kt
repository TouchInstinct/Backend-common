@file:Suppress("unused")
package ru.touchin.auth.core.configurations

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import ru.touchin.common.spring.jpa.EnableJpaAuditingExtra

@EntityScan("ru.touchin.auth.core")
@EnableJpaRepositories("ru.touchin.auth.core")
@EnableCaching
@EnableJpaAuditingExtra
class AuthCoreDatabaseConfiguration
