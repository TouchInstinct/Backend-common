@file:Suppress("SpringFacetCodeInspection")
package ru.touchin.common.spring.test.jpa.repository

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import ru.touchin.common.spring.jpa.EnableJpaAuditingExtra
import javax.sql.DataSource

@TestConfiguration
@EnableJpaAuditingExtra
@ComponentScan
class RepositoryTestConfig {

    // запуск и остановка контейнера по lifecycle-событиями компонента (1)
    @Bean(initMethod = "start", destroyMethod = "stop")
    fun jdbcDatabaseContainer(): JdbcDatabaseContainer<*> {
        return PostgreSQLContainer<Nothing>("postgres:12").apply {
            waitingFor(Wait.forListeningPort())
        }
    }

    @Bean
    fun dataSource(jdbcDatabaseContainer: JdbcDatabaseContainer<*>): DataSource {
        val hikariConfig = HikariConfig()
        hikariConfig.jdbcUrl = jdbcDatabaseContainer.jdbcUrl
        hikariConfig.username = jdbcDatabaseContainer.username
        hikariConfig.password = jdbcDatabaseContainer.password
        return HikariDataSource(hikariConfig)
    }
}
