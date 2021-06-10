package ru.touchin.settings

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import ru.touchin.settings.configurations.SettingsDatabaseConfiguration

@Profile("test-slow")
@EnableAutoConfiguration
@TestConfiguration
@ComponentScan
@Import(SettingsDatabaseConfiguration::class)
class SettingsSlowTestConfiguration {


}
