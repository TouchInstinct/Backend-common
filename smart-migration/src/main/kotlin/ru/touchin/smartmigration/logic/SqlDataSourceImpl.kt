package ru.touchin.smartmigration.logic

import org.intellij.lang.annotations.Language

private const val MIGRATION_TABLE_NAME = "SMART_MIGRATION"

class SqlDatasourceImpl:DataSourceSQL {

    @Language("TSQL")
    override fun getTableCheckSQL(): String {
        return """
                  IF NOT EXISTS (
                      SELECT * FROM sysobjects WHERE name = '$MIGRATION_TABLE_NAME' and xtype='U'
                  )
                  CREATE TABLE SMART_MIGRATION  (
                  ID BIGINT PRIMARY KEY IDENTITY , 
                  BUILD_NUMBER VARCHAR(255) NOT NULL,
                  DATE DATETIME NOT NULL
                  );
               """
    }

    @Language("TSQL")
    override fun getMigrationCheckSQL(buildNumber: String): String {
        return "SELECT * FROM $MIGRATION_TABLE_NAME WHERE BUILD_NUMBER = '$buildNumber'"
    }

    @Language("TSQL")
    override fun getInsertMigrationSQL(buildNumber: String, formattedTime: String): String {
        return """
                  INSERT INTO $MIGRATION_TABLE_NAME (BUILD_NUMBER, DATE)
                  VALUES ('$buildNumber', '$formattedTime');
               """
    }

}


