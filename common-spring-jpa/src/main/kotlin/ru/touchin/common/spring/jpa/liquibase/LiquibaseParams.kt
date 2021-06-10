package ru.touchin.common.spring.jpa.liquibase

data class LiquibaseParams(
    val schema: String,
    val changeLogPath: String,
)
