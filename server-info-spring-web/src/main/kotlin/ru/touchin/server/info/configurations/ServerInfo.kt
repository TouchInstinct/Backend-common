package ru.touchin.server.info.configurations

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Suppress("SpringFacetCodeInspection")
@Configuration
@ComponentScan("ru.touchin.server.info.advices", "ru.touchin.server.info.controllers")
@ConfigurationPropertiesScan("ru.touchin.server.info")
class ServerInfo
