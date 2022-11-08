package ru.touchin.push.message.provider.fcm.configurations

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.google.auth.oauth2.AccessToken
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.core.io.ClassPathResource
import ru.touchin.push.message.provider.configurations.PushMessageProviderConfiguration
import ru.touchin.push.message.provider.fcm.properties.PushMessageProviderFcmProperties
import java.text.SimpleDateFormat
import java.util.*

@ComponentScan("ru.touchin.push.message.provider.fcm")
@ConfigurationPropertiesScan(basePackages = ["ru.touchin.push.message.provider.fcm"])
@Import(value = [PushMessageProviderConfiguration::class])
class PushMessageProviderFcmConfiguration {

    @Bean
    fun firebaseMessaging(
        properties: PushMessageProviderFcmProperties,
        @Qualifier("push-message-provider.fcm.credentials-object-mapper")
        objectMapper: ObjectMapper
    ): FirebaseMessaging {
        val credentials = when {
            properties.auth.credentialsData != null -> {
                GoogleCredentials.fromStream(
                    objectMapper.writeValueAsString(properties.auth.credentialsData).byteInputStream(Charsets.UTF_8)
                )
            }

            properties.auth.credentialsFile != null -> {
                GoogleCredentials.fromStream(
                    ClassPathResource(properties.auth.credentialsFile.path).inputStream
                )
            }

            properties.auth.token != null -> {
                GoogleCredentials.create(
                    AccessToken(properties.auth.token.value, properties.auth.token.expiresAt)
                )
            }

            else -> throw IllegalStateException("No more authorization types allowed.")
        }

        val options: FirebaseOptions = FirebaseOptions.builder()
            .setCredentials(credentials)
            .setConnectTimeout(properties.client.connectionTimeout.toMillis().toInt())
            .setReadTimeout(properties.client.readTimeout.toMillis().toInt())
            .build()

        val firebaseApp: FirebaseApp = FirebaseApp.initializeApp(options, properties.appName)

        return FirebaseMessaging.getInstance(firebaseApp)
    }

    @Bean("push-message-provider.fcm.credentials-date-format")
    fun simpleDateFormat(): SimpleDateFormat {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss X", Locale.getDefault())
    }

    @Bean("push-message-provider.fcm.credentials-object-mapper")
    fun objectMapper(
        @Qualifier("push-message-provider.fcm.credentials-date-format")
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
