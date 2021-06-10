@file:Suppress("SpringFacetCodeInspection")
package ru.touchin.common.spring.test.jpa.repository

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import ru.touchin.common.spring.jpa.EnableJpaAuditingExtra
import javax.sql.DataSource

@Profile("test-slow")
@TestConfiguration
@EnableJpaAuditingExtra
@ComponentScan
class RepositoryTestConfiguration {

    // запуск и остановка контейнера по lifecycle-событиями компонента (1)
    @Bean(initMethod = "start", destroyMethod = "stop")
    fun jdbcDatabaseContainer(
        @Value("\${tests.slow.db.imageName}") imageName: String,
    ): JdbcDatabaseContainer<*> {
        return PostgreSQLContainer<Nothing>(imageName).apply {
            waitingFor(Wait.forListeningPort())
        }
    }

    @Bean
    fun dataSource(jdbcDatabaseContainer: JdbcDatabaseContainer<*>): DataSource {
        val hikariConfig = HikariConfig()
            .apply {
                jdbcUrl = jdbcDatabaseContainer.jdbcUrl
                username = jdbcDatabaseContainer.username
                password = jdbcDatabaseContainer.password
            }

        return HikariDataSource(hikariConfig)
    }

}
