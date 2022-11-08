package ru.touchin.push.message.provider.fcm

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.google.firebase.FirebaseApp
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.event.ContextRefreshedEvent
import java.text.SimpleDateFormat

@TestConfiguration
@SpringBootConfiguration
@EnablePushMessageProviderFcm
class PushMessageProviderFcmTestApplication : ApplicationListener<ContextRefreshedEvent> {

    @Bean("push-message-provider.fcm.credentials-object-mapper")
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

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        clearSingletonsOutsideContainer()
    }

    private fun clearSingletonsOutsideContainer() {
        FirebaseApp.getApps().forEach(FirebaseApp::delete)
    }

}
