package ru.touchin.push.message.provider

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import ru.touchin.push.message.provider.configurations.PushMessageProviderConfiguration

@TestConfiguration
@SpringBootConfiguration
@ContextConfiguration(classes = [PushMessageProviderConfiguration::class])
@Import(PushMessageProviderConfiguration::class)
class PushMessageProviderTestApplication
