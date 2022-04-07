package ru.touchin.info.configurations

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Suppress("SpringFacetCodeInspection")
@Configuration
@ComponentScan("ru.touchin.info.advices", "ru.touchin.info.services")
class ServerInfo
