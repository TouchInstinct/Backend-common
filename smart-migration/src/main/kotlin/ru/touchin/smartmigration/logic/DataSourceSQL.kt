package ru.touchin.smartmigration.logic

interface DataSourceSQL {

    fun getTableCheckSQL(): String

    fun getMigrationCheckSQL(buildNumber: String): String

    fun getInsertMigrationSQL(buildNumber: String, formattedTime: String): String

}
