package ru.touchin.smartmigration.logic

private const val MIGRATION_TABLE_NAME = "SMART_MIGRATION"

class PostgresDataSourceImpl: DataSourceSQL {
    override fun getTableCheckSQL(): String {
        return "CREATE TABLE IF NOT EXISTS smart_migration  (\n" +
            "ID BIGSERIAL PRIMARY KEY ,\n" +
            "BUILD_NUMBER VARCHAR(255) NOT NULL,\n" +
            "DATE timestamp NOT NULL" +
            ");"
    }

    override fun getMigrationCheckSQL(buildNumber: String): String {
        return "SELECT * FROM $MIGRATION_TABLE_NAME WHERE BUILD_NUMBER = '$buildNumber'"
    }

    override fun getInsertMigrationSQL(buildNumber: String, formattedTime: String): String {
        return "INSERT INTO $MIGRATION_TABLE_NAME (BUILD_NUMBER, DATE)\n" +
            "VALUES ('$buildNumber', '$formattedTime');"
    }
}
