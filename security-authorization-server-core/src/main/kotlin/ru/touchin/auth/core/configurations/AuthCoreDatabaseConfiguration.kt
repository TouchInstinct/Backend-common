@file:Suppress("unused")
package ru.touchin.auth.core.configurations

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import ru.touchin.common.spring.jpa.EnableJpaAuditingExtra
import ru.touchin.common.spring.jpa.liquibase.LiquibaseParams

@EntityScan("ru.touchin.auth.core")
@EnableJpaRepositories("ru.touchin.auth.core")
@ComponentScan("ru.touchin.common.spring.jpa.liquibase")
@EnableCaching
@EnableJpaAuditingExtra
class AuthCoreDatabaseConfiguration {

    companion object {
        const val SCHEMA: String = "auth"
    }

    @Bean
    fun liquibaseParams(): LiquibaseParams {
        return LiquibaseParams(
            schema = SCHEMA,
            changeLogPath = "auth/db/changelog/db.changelog-master.yaml",
        )
    }

}
