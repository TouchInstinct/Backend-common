package ru.touchin.settings.configurations

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import ru.touchin.settings.annotations.SettingMapper

@Configuration
@ComponentScan(
    "ru.touchin.settings.services"
)
class SettingsConfiguration {

    @Bean
    @SettingMapper
    fun getSettingMapper(): ObjectMapper {
        return ObjectMapper()
            .registerModule(KotlinModule())
            .setSerializationInclusion(JsonInclude.Include.ALWAYS)
    }

}
