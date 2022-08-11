package ru.touchin.push.message.provider.fcm.configurations

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.core.io.ClassPathResource
import ru.touchin.push.message.provider.configurations.PushMessageProviderConfiguration
import ru.touchin.push.message.provider.fcm.properties.PushMessageProviderFcmProperties

@ComponentScan("ru.touchin.push.message.provider.fcm")
@ConfigurationPropertiesScan(basePackages = ["ru.touchin.push.message.provider.fcm"])
@Import(value = [PushMessageProviderConfiguration::class])
class PushMessageProviderFcmConfiguration {

    @Bean
    fun firebaseMessaging(
        properties: PushMessageProviderFcmProperties
    ): FirebaseMessaging {
        val credentials = GoogleCredentials.fromStream(ClassPathResource(properties.auth.resourcePath).inputStream)

        val options: FirebaseOptions = FirebaseOptions.builder()
            .setCredentials(credentials)
            .setConnectTimeout(properties.client.connectionTimeout.toMillis().toInt())
            .setReadTimeout(properties.client.readTimeout.toMillis().toInt())
            .build()

        val firebaseApp: FirebaseApp = FirebaseApp.initializeApp(options, properties.appName)

        return FirebaseMessaging.getInstance(firebaseApp)
    }

}
