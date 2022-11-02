package ru.touchin.smartmigration

import org.springframework.stereotype.Component
import ru.touchin.common.spring.annotations.RequiredBy
import ru.touchin.smartmigration.logic.DataSourceSQL
import ru.touchin.smartmigration.logic.factory.DataSourceSqlFactoryImpl
import java.sql.Date
import java.sql.ResultSet
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

        val checkBuildNumber = dataSourceSql.getMigrationCheckSQL(buildNumber)

        val result: ResultSet = dataSource.connection.createStatement().executeQuery(checkBuildNumber)
        var rowCount = 0
        while (result.next()) {
            rowCount += 1;
        }
        return rowCount != 0
    }

    private fun checkMigrationTable() {
        val createTable = dataSourceSql.getTableCheckSQL()
        dataSource.connection.createStatement()
            .execute(createTable)
    }

    private fun insertMigration(buildNumber: String) {
        val insertMigration =dataSourceSql.getInsertMigrationSQL(buildNumber, CURRENT_TIME_SQL)
        dataSource.connection.createStatement()
            .execute(insertMigration)
    }

}
