package ru.touchin.smartmigration.logic.factory

import ru.touchin.smartmigration.logic.DataSourceSQL
import ru.touchin.smartmigration.logic.PostgresDataSourceImpl
import ru.touchin.smartmigration.logic.SqlDatasourceImpl

class DataSourceSqlFactoryImpl: DataSourceSqlFactory {

    override fun getDataSourceSql(driverName: String): DataSourceSQL {
        return when(driverName){
            "Microsoft SQL Server" -> SqlDatasourceImpl()
            "PostgresSQL" -> PostgresDataSourceImpl()
            else -> {
                PostgresDataSourceImpl()
            }
        }
    }
}
