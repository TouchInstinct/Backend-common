package ru.touchin.settings.services

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.settings.annotations.SettingMapper
import ru.touchin.settings.dto.SystemSetting
import ru.touchin.settings.exceptions.CannotParseSettingValueException
import ru.touchin.settings.models.SystemSettingModel
import ru.touchin.settings.repositories.SystemSettingsRepository
import ru.touchin.settings.repositories.findByIdOrThrow

@Service
class SystemSettingsServiceImpl(
    private val systemSettingsRepository: SystemSettingsRepository,
    @SettingMapper
    private val settingsObjectMapper: ObjectMapper,
) : SystemSettingsService {

    @Transactional
    override fun <T> save(setting: SystemSetting<T>): SystemSetting<T> {
        val settingModel = systemSettingsRepository.findByIdOrNull(setting.key)
            ?: SystemSettingModel().apply {
                key = setting.key
            }

        settingModel.value = settingsObjectMapper.writeValueAsString(setting.value)

        systemSettingsRepository.save(settingModel)

        return setting
    }

    private fun <T> createSetting(model: SystemSettingModel, clazz: Class<T>): SystemSetting<T> {
        val value = kotlin
            .runCatching {
                settingsObjectMapper.readValue(model.value, clazz)
            }
            .recoverCatching { exception ->
                when(exception) {
                    is JsonProcessingException,
                    is JsonMappingException -> {
                        throw CannotParseSettingValueException(model.value, clazz, exception)
                    }

                    else -> {
                        throw exception
                    }
                }
            }
            .getOrThrow()

        return SystemSetting(
            key = model.key,
            value = value,
        )
    }

    @Transactional(readOnly = true)
    override fun <T> getOrNull(settingKey: String, clazz: Class<T>): SystemSetting<T>? {
        return systemSettingsRepository.findByIdOrNull(settingKey)
            ?.let { createSetting(it, clazz) }
    }

    @Transactional(readOnly = true)
    override fun <T> get(settingKey: String, clazz: Class<T>): SystemSetting<T> {
        return createSetting(systemSettingsRepository.findByIdOrThrow(settingKey), clazz)
    }

    @Transactional
    override fun delete(settingKey: String) {
        return systemSettingsRepository.deleteById(settingKey)
    }

}

