package ru.touchin.settings.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import ru.touchin.settings.annotations.SettingMapper
import ru.touchin.settings.dto.SystemSetting
import ru.touchin.settings.models.SystemSettingEntity
import ru.touchin.settings.repositories.SystemSettingsRepository
import java.util.*
import javax.annotation.PostConstruct

@ActiveProfiles("test")
@SpringBootTest
internal class SystemSettingsServiceImplSerializationTest {

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


    private fun check(systemSetting: SystemSetting<*>, serializedValue: String) {
        doReturn(Optional.empty<SystemSettingEntity>()).`when`(systemSettingsRepository).findById(any())
        doAnswer { it.getArgument(0) as SystemSettingEntity }.`when`(systemSettingsRepository).save(any())

        systemSettingsService.save(systemSetting)

        verify(systemSettingsRepository).save(argThat {
            value == serializedValue
        })
    }


    @Test
    fun stringShouldBeSerialized() {
        val systemSetting = SystemSetting(
            key = "setting.string",
            value = "hello"
        )

        check(systemSetting, "\"hello\"")
    }

    @Test
    fun intShouldBeSerialized() {
        val systemSetting = SystemSetting(
            key = "setting.int",
            value = 23
        )

        check(systemSetting, "23")
    }

    @Test
    fun nullShouldBeSerialized() {
        val systemSetting = SystemSetting(
            key = "setting.null",
            value = null
        )

        check(systemSetting, "null")
    }


    @Test
    fun arrayShouldBeSerialized() {
        val systemSetting = SystemSetting(
            key = "setting.array",
            value = arrayOf(1,2,3)
        )

        check(systemSetting, "[1,2,3]")
    }

    @Test
    fun listShouldBeSerialized() {
        val systemSetting = SystemSetting(
            key = "setting.list",
            value = listOf(1,2,3)
        )

        check(systemSetting, "[1,2,3]")
    }


    @Test
    fun objectShouldBeSerialized() {
        data class UserObject(val name: String, val age: Int)

        val systemSetting = SystemSetting(
            key = "setting.object",
            value = UserObject("mike", 32)
        )

        check(systemSetting, "{\"name\":\"mike\",\"age\":32}")
    }

}
