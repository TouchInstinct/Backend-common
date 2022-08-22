package ru.touchin.push.message.provider.fcm

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import java.text.SimpleDateFormat

@TestConfiguration
@SpringBootConfiguration
@EnablePushMessageProviderFcm
class PushMessageProviderFcmTestApplication {

    @Bean
    fun objectMapper(
        @Qualifier("push-message-provider.fcm.auth")
        simpleDateFormat: SimpleDateFormat
    ): ObjectMapper {
        return ObjectMapper().apply {
            configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
            setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            dateFormat = simpleDateFormat
        }
    }

}
