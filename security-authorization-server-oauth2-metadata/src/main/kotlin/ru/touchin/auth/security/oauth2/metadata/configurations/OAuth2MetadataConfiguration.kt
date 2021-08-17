package ru.touchin.auth.security.oauth2.metadata.configurations

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan

@ComponentScan("ru.touchin.auth.security.oauth2.metadata")
@ConfigurationPropertiesScan("ru.touchin.auth.security.oauth2.metadata.properties")
class OAuth2MetadataConfiguration
