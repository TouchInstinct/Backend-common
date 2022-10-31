package ru.touchin.smartmigration

import org.intellij.lang.annotations.Language
import org.springframework.stereotype.Component
import ru.touchin.common.spring.annotations.RequiredBy
import java.sql.Date
import java.sql.ResultSet
import java.text.SimpleDateFormat
import javax.annotation.PostConstruct
import javax.sql.DataSource

private const val MIGRATION_TABLE_NAME = "SMART_MIGRATION"

val CURRENT_TIME_SQL: String
    get() = Date(System.currentTimeMillis()).let { date ->
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date)
    }

@Component
@RequiredBy("liquibase")
class BeforeLiquibase(
    private val dataSource: DataSource
) {

    @PostConstruct
    fun doAction() {
        val buildNumber = System.getenv("BUILD_NUMBER")
        if (buildNumber != null) {
            checkMigrationTable()
            if (checkBuildMigrationExecuted(buildNumber)) {
                System.setProperty("spring.liquibase.enabled", "false")
            } else {
                insertMigration(buildNumber)
            }
        }
    }

    private fun checkBuildMigrationExecuted(buildNumber: String): Boolean {
        @Language("TSQL")
        val checkBuildNumber = "SELECT * FROM $MIGRATION_TABLE_NAME WHERE BUILD_NUMBER = '$buildNumber'"

        val result: ResultSet = dataSource.connection.createStatement().executeQuery(checkBuildNumber)
        var rowCount = 0
        while (result.next()) {
            rowCount += 1;
        }
        return rowCount != 0
    }

    private fun checkMigrationTable() {
        @Language("TSQL")
        val createTable = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name = '$MIGRATION_TABLE_NAME' and xtype='U')" +
                "CREATE TABLE SMART_MIGRATION  (\n" +
                "\"ID\" BIGINT PRIMARY KEY IDENTITY ,\n" +
                "\"BUILD_NUMBER\" VARCHAR(255) NOT NULL,\n" +
                "\"DATE\" DATETIME NOT NULL" +
                ");"

        dataSource.connection.createStatement()
            .execute(createTable)
    }

    private fun insertMigration(buildNumber: String) {
        @Language("TSQL")
        val insertMigration = "INSERT INTO $MIGRATION_TABLE_NAME (BUILD_NUMBER, DATE)\n" +
            "VALUES ('$buildNumber', '$CURRENT_TIME_SQL');"

        dataSource.connection.createStatement()
            .execute(insertMigration)
    }
}
