@file:Suppress("unused")

package ru.touchin.common.spring.jpa.liquibase

import liquibase.Contexts
import liquibase.Liquibase
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.springframework.stereotype.Component
import ru.touchin.common.spring.annotations.RunOnceOnStartup
import javax.sql.DataSource

@Component
class LiquibaseStart(
    private val dataSource: DataSource,
    private val liquibaseParams: LiquibaseParams,
) {

    @RunOnceOnStartup
    fun runLiquibase() {
        dataSource.connection.use { connection ->
            val database: Database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(JdbcConnection(connection))
                .apply { defaultSchemaName = liquibaseParams.schema }

            val liquibase = Liquibase(liquibaseParams.changeLogPath, ClassLoaderResourceAccessor(), database)

            liquibase.update(Contexts())
        }
    }

}
