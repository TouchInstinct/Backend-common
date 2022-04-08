package ru.touchin.server.info.configurations

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Suppress("SpringFacetCodeInspection")
@Configuration
@ComponentScan("ru.touchin.server.info.advices")
@ConfigurationPropertiesScan("ru.touchin.server.info")
class ServerInfo
