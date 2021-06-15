@file:Suppress("unused")

package ru.touchin.settings

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import ru.touchin.settings.configurations.SettingsConfiguration

@SpringBootConfiguration
@ContextConfiguration(classes = [SettingsConfiguration::class])
@TestConfiguration
@Import(SettingsConfiguration::class, SettingsSlowTestConfiguration::class)
class SettingsTestApplication
