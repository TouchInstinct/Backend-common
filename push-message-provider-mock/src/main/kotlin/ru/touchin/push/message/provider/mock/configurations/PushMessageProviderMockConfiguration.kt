package ru.touchin.push.message.provider.mock.configurations

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan

@ComponentScan("ru.touchin.push.message.provider.mock")
@ConfigurationPropertiesScan(basePackages = ["ru.touchin.push.message.provider.mock.properties"])
class PushMessageProviderMockConfiguration
