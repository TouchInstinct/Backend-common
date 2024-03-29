@file:Suppress("unused")

package ru.touchin.common.spring.jpa.liquibase

import liquibase.Contexts
import liquibase.Liquibase
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.integration.spring.SpringResourceAccessor
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.stereotype.Component
import ru.touchin.common.spring.annotations.RunOnceOnStartup
import javax.sql.DataSource

@Component
class LiquibaseStart(
    private val dataSource: DataSource,
    private val liquibaseParams: List<LiquibaseParams>,
) {

    @RunOnceOnStartup
    fun runLiquibase() {
        liquibaseParams.forEach { params ->
            dataSource.connection.use { connection ->
                val database: Database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(JdbcConnection(connection))
                    .apply {
                        defaultSchemaName = params.schema
                    }

                val resourceAccessor = SpringResourceAccessor(DefaultResourceLoader())

                val liquibase = Liquibase(params.changeLogPath, resourceAccessor, database)

                liquibase.update(Contexts())
            }
        }
    }

}
