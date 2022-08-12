package ru.touchin.push.message.provider.configurations

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan

@ComponentScan("ru.touchin.push.message.provider")
@ConfigurationPropertiesScan(basePackages = ["ru.touchin.push.message.provider"])
class PushMessageProviderConfiguration
