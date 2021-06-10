package ru.touchin.settings.configurations

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import ru.touchin.common.spring.jpa.EnableJpaAuditingExtra
import ru.touchin.common.spring.jpa.liquibase.LiquibaseParams

@Configuration
@ComponentScan("ru.touchin.common.spring.jpa.liquibase")
@EntityScan("ru.touchin.settings.models")
@EnableJpaRepositories(basePackages = ["ru.touchin.settings.repositories"])
@EnableJpaAuditingExtra
class SettingsDatabaseConfiguration {

    companion object {
        const val SCHEMA: String = "settings"
    }

    @Bean
    fun liquibaseParams(): LiquibaseParams {
        return LiquibaseParams(
            schema = SCHEMA,
            changeLogPath = "settings/db/changelog/db.changelog-master.yaml",
        )
    }

}
