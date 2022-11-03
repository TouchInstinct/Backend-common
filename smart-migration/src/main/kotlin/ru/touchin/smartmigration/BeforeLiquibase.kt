@file:Suppress("unused")
package ru.touchin.smartmigration

import org.springframework.stereotype.Component
import ru.touchin.common.spring.annotations.RequiredBy
import ru.touchin.smartmigration.logic.DataSourceSQL
import ru.touchin.smartmigration.logic.factory.DataSourceSqlFactoryImpl
import java.sql.Date
import java.text.SimpleDateFormat
import javax.annotation.PostConstruct
import javax.sql.DataSource

private val SQL_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

private val CURRENT_TIME_SQL: String
    get() = SQL_DATE_FORMAT.format(Date(System.currentTimeMillis()))

@Component
@RequiredBy("liquibase")
class BeforeLiquibase(
    private val dataSource: DataSource
) {

    val dataSourceSql: DataSourceSQL = DataSourceSqlFactoryImpl()
        .getDataSourceSql(dataSource.connection.metaData.databaseProductName)

    @PostConstruct
    fun doAction() {
        val buildNumber = System.getenv("BUILD_NUMBER")
        ?: return

        checkMigrationTable()

        if (checkBuildMigrationExecuted(buildNumber)) {
            System.setProperty("spring.liquibase.enabled", "false")
        } else {
            insertMigration(buildNumber)
        }
    }

    private fun checkBuildMigrationExecuted(buildNumber: String): Boolean {
        return dataSourceSql.getMigrationCheckSQL(buildNumber).let {
            dataSource.connection.createStatement().executeQuery(it).next()
        }
    }

    private fun checkMigrationTable() {
        dataSourceSql.getTableCheckSQL().let {
            dataSource.connection.createStatement().execute(it)
        }
    }

    private fun insertMigration(buildNumber: String) {
        dataSourceSql.getInsertMigrationSQL(buildNumber, CURRENT_TIME_SQL).let {
            dataSource.connection.createStatement().execute(it)
        }
    }

}
