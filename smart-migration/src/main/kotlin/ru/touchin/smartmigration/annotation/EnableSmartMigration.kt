package ru.touchin.smartmigration.annotation

import org.springframework.context.annotation.Import
import ru.touchin.smartmigration.SmartMigrationConfig

@Import(SmartMigrationConfig::class)
annotation class EnableSmartMigration
