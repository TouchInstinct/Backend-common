package ru.touchin.settings.models

import ru.touchin.settings.configurations.SettingsDatabaseConfiguration.Companion.SCHEMA
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "system_settings", schema = SCHEMA)
class SystemSettingModel : AbstractSettingModel()
