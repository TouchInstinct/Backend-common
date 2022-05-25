package ru.touchin.geoip.core.configurations

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan

@ConfigurationPropertiesScan("ru.touchin.geoip.core")
@ComponentScan("ru.touchin.geoip.core")
class GeoipCoreConfiguration
