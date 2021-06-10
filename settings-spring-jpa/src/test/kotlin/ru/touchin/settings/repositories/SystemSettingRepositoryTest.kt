package ru.touchin.settings.repositories

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import org.springframework.beans.factory.annotation.Autowired
import ru.touchin.common.spring.test.jpa.repository.RepositoryTest
import ru.touchin.settings.exceptions.SettingNotFoundException
import ru.touchin.settings.models.SystemSettingEntity
import javax.persistence.EntityManager

@RepositoryTest
internal class SystemSettingRepositoryTest {

    @Autowired
    private lateinit var systemSettingsRepository: SystemSettingsRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @DisplayName("Настройки должны сохраняться в базе")
    fun shouldBeSaved() {
        val setting = SystemSettingEntity()
            .apply {
                key = "max.threads"
                value = "5"
            }

        systemSettingsRepository.save(setting)

        // если не вызывать clear, то репозиторий возвращает тот же самый объект и кеша
        entityManager.apply {
            flush()
            clear()
        }

        val actualSetting = systemSettingsRepository.findByIdOrThrow(setting.key)

        Assertions.assertTrue(ReflectionEquals(setting, "createdAt").matches(actualSetting))
    }

    @Test
    @DisplayName("Если настройки не найдены, должна быть ошибка SettingNotFoundException")
    fun shouldBeSettingNotFoundException() {
        assertThrows<SettingNotFoundException> {
            systemSettingsRepository.findByIdOrThrow("missing")
        }
    }

}
