package ru.touchin.spring.workers.manager.core.config

import liquibase.Contexts
import liquibase.Liquibase
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.springframework.stereotype.Component
import ru.touchin.spring.workers.manager.WorkersManagerConfiguration.Companion.SCHEMA
import javax.sql.DataSource

@Component
class LiquibaseRunner(
    private val dataSource: DataSource
) {

    fun run() = dataSource.connection.use { connection ->
        val database: Database = DatabaseFactory.getInstance()
            .findCorrectDatabaseImplementation(JdbcConnection(connection))
            .apply { defaultSchemaName = SCHEMA }

        val liquibase = Liquibase(MASTER_CHANGELOG_PATH, ClassLoaderResourceAccessor(), database)

        liquibase.changeLogParameters.set("schemaName", SCHEMA)

        liquibase.update(Contexts())
    }

    companion object {
        private const val MASTER_CHANGELOG_PATH = "workers/db/changelog/db.changelog-master.yaml"
    }

}
