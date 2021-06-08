package ru.touchin.version.configurations

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.touchin.version.mapping.WebMvcVersionRegistrations

@Suppress("SpringFacetCodeInspection")
@Configuration
class SpringVersionConfiguration {

    @Bean
    fun webMvcRegistrations(): WebMvcRegistrations {
        return WebMvcVersionRegistrations()
    }

}
