package ru.touchin.geoip.core.configurations

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import ru.touchin.common.spring.jpa.liquibase.LiquibaseParams

@ComponentScan("ru.touchin.common.spring.jpa.liquibase")
@EntityScan("ru.touchin.geoip.core")
@EnableJpaRepositories(value = ["ru.touchin.geoip.core"])
@EnableTransactionManagement
class GeoipCoreDatabaseConfiguration {

    @Bean
    fun liquibaseParams(): LiquibaseParams {
        return LiquibaseParams(
            schema = SCHEMA,
            changeLogPath = "geoip/db/changelog/db.changelog-master.yaml",
        )
    }

    companion object {
        const val SCHEMA: String = "geoip"
    }

}
