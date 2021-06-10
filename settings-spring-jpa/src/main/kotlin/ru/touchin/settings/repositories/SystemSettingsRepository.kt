package ru.touchin.settings.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import ru.touchin.settings.exceptions.SettingNotFoundException
import ru.touchin.settings.models.SystemSettingEntity

interface SystemSettingsRepository: JpaRepository<SystemSettingEntity, String>

fun SystemSettingsRepository.findByIdOrThrow(key: String): SystemSettingEntity {
    return findByIdOrNull(key)
        ?: throw SettingNotFoundException(key)
}
