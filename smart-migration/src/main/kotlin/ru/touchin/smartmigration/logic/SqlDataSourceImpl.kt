package ru.touchin.smartmigration.logic

private const val MIGRATION_TABLE_NAME = "SMART_MIGRATION"

class SqlDatasourceImpl:DataSourceSQL {
    override fun getTableCheckSQL(): String {
        return "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name = '$MIGRATION_TABLE_NAME' and xtype='U')" +
            "CREATE TABLE SMART_MIGRATION  (\n" +
            "\"ID\" BIGINT PRIMARY KEY IDENTITY ,\n" +
            "\"BUILD_NUMBER\" VARCHAR(255) NOT NULL,\n" +
            "\"DATE\" DATETIME NOT NULL" +
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


