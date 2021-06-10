package ru.touchin.settings.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import ru.touchin.settings.annotations.SettingMapper
import ru.touchin.settings.dto.SystemSetting
import ru.touchin.settings.models.SystemSettingEntity
import ru.touchin.settings.repositories.SystemSettingsRepository
import java.util.*
import javax.annotation.PostConstruct

internal data class UserObject(val name: String, val age: Int)

@ActiveProfiles("test")
@SpringBootTest
internal class SystemSettingsServiceImplDeserializationTest {


    @Autowired
    @SettingMapper
    private lateinit var settingMapper: ObjectMapper

    private val systemSettingsRepository: SystemSettingsRepository = mock {}

    private lateinit var systemSettingsService: SystemSettingsService

    @PostConstruct
    fun init() {
        systemSettingsService = spy(
            SystemSettingsServiceImpl(
                systemSettingsRepository = systemSettingsRepository,
                settingsObjectMapper = settingMapper
            )
        )
    }


    private fun <T> check(systemSetting: SystemSetting<T>, serializedValue: String, assert: (T, T) -> Unit = { e, a ->
        assertTrue(ReflectionEquals(e).matches(a))
    }) {
        doReturn(
            Optional.of(SystemSettingEntity().apply {
                key = systemSetting.key
                value = serializedValue
            })
        ).`when`(systemSettingsRepository).findById(any())

        systemSetting.value
            ?.let { actualValue ->
                val actualSystemSetting = systemSettingsService.get(systemSetting.key, actualValue::class.java)

                assert(systemSetting.value, actualSystemSetting.value)
            }
            ?: run {
                assertNull(systemSettingsService.get(systemSetting.key, ""::class.java).value)
            }
    }


    @Test
    fun stringShouldBeDeserialized() {
        val systemSetting = SystemSetting(
            key = "setting.string",
            value = "hello"
        )

        check(systemSetting, "\"hello\"") { expected, actual ->
            assertEquals(expected, actual)
        }
    }

    @Test
    fun intShouldBeDeserialized() {
        val systemSetting = SystemSetting(
            key = "setting.int",
            value = 23
        )

        check(systemSetting, "23")
    }

    @Test
    fun nullShouldBeDeserialized() {
        val systemSetting = SystemSetting(
            key = "setting.null",
            value = null
        )

        check(systemSetting, "null")
    }


    @Test
    fun arrayShouldBeDeserialized() {
        val systemSetting = SystemSetting(
            key = "setting.array",
            value = arrayOf(1,2,3)
        )

        check(systemSetting, "[1,2,3]")
    }

    @Test
    fun listShouldBeDeserialized() {
        val systemSetting = SystemSetting(
            key = "setting.list",
            value = listOf(1,2,3)
        )

        check(systemSetting, "[1,2,3]") { expected, actual ->
            assertEquals(expected, actual)
        }
    }


    @Test
    fun objectShouldBeDeserialized() {
        val systemSetting = SystemSetting(
            key = "setting.object",
            value = UserObject("mike", 32)
        )

        check(systemSetting, "{\"name\":\"mike\",\"age\":32}")
    }

}
