package ru.touchin.spring.workers.manager

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * Configuration which brings to context all the components, required to support workers manager module via annotations.
 *
 * You could @[org.springframework.context.annotation.Import] this configuration into your application or use [EnableWorkersManager] annotation to do it automatically
 */
@ComponentScan
@EntityScan
@EnableJpaRepositories
@EnableCaching
@ConfigurationPropertiesScan
class WorkersManagerConfiguration {

    companion object {
        const val SCHEMA: String = "workers"
    }

    /**
     * Applies `@EnableJpaAuditing` only if it was not already applied.
     * Enabling `@EnableJpaAuditing` twice will lead to application context failure.
     */
    @Configuration
    @ConditionalOnMissingBean(name=["jpaAuditingHandler"])
    @EnableJpaAuditing
    class JpaAuditingNonConflictingDeclaration

}
