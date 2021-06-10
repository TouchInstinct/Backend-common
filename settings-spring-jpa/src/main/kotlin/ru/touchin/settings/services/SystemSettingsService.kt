package ru.touchin.settings.services

import ru.touchin.settings.dto.SystemSetting

interface SystemSettingsService {

    fun <T> save(setting: SystemSetting<T>): SystemSetting<T>

    fun <T> getOrNull(settingKey: String, clazz: Class<T>): SystemSetting<T>?

    fun <T> get(settingKey: String, clazz: Class<T>): SystemSetting<T>

    fun delete(settingKey: String)

}
