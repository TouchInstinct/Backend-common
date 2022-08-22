package ru.touchin.push.message.provider.fcm.configurations

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import ru.touchin.push.message.provider.fcm.properties.PushMessageProviderFcmProperties
import java.lang.IllegalArgumentException
import java.util.*

class PushMessageProviderFcmPropertiesAuthTest {

    @Test
    @DisplayName("Один тип авторизации может быть выбран")
    fun constructor_singleConfigurationShouldBeOk() {
        PushMessageProviderFcmProperties.Auth(
            credentialsFile = null,
            credentialsData = null,
            token = PushMessageProviderFcmProperties.AccessToken(
                value = "testToken",
                expiresAt = Date()
            )
        )
    }

    @Test
    @DisplayName("При отсутствии типов авторизации выбрасывается исключение")
    fun constructor_configurationMustExist() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            PushMessageProviderFcmProperties.Auth(
                credentialsFile = null,
                credentialsData = null,
                token = null
            )
        }
    }

    @Test
    @DisplayName("При нескольких типах авторизации выбрасывается исключение")
    fun constructor_configurationMustBeSingle() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            PushMessageProviderFcmProperties.Auth(
                credentialsFile = PushMessageProviderFcmProperties.CredentialsFile(
                    path = "testPath"
                ),
                credentialsData = null,
                token = PushMessageProviderFcmProperties.AccessToken(
                    value = "testToken",
                    expiresAt = Date()
                )
            )
        }
    }

}
