package ru.touchin.smartmigration.logic.factory

import ru.touchin.smartmigration.logic.DataSourceSQL

interface DataSourceSqlFactory {

    fun getDataSourceSql(driverName: String): DataSourceSQL

}
