package ru.touchin.push.message.provider.fcm

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import ru.touchin.push.message.provider.fcm.configurations.PushMessageProviderFcmConfiguration

@SpringBootConfiguration
@ContextConfiguration(classes = [PushMessageProviderFcmConfiguration::class])
@TestConfiguration
@Import(PushMessageProviderFcmConfiguration::class)
@ConfigurationPropertiesScan(basePackages = ["ru.touchin.push.message.provider.fcm"])
class PushMessageProviderFcmTestApplication {

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().apply {
            configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
            setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
        }
    }

}
