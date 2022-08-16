package ru.touchin.push.message.provider.factories

import org.junit.Assert
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import ru.touchin.common.exceptions.CommonException
import ru.touchin.push.message.provider.dto.request.PushTokenCheck
import ru.touchin.push.message.provider.dto.request.SendPushRequest
import ru.touchin.push.message.provider.dto.result.CheckPushTokenResult
import ru.touchin.push.message.provider.dto.result.SendPushResult
import ru.touchin.push.message.provider.enums.PlatformType
import ru.touchin.push.message.provider.enums.PushMessageProviderType
import ru.touchin.push.message.provider.properties.PushMessageProviderProperties
import ru.touchin.push.message.provider.services.PushMessageProviderService

class PushMessageProviderServiceFactoryImplTest {

    private val pushMessageProviderServiceFcm = object : PushMessageProviderService {

        override val type: PushMessageProviderType = PushMessageProviderType.FCM

        override fun send(request: SendPushRequest): SendPushResult = throw NotImplementedError()

        override fun check(request: PushTokenCheck): CheckPushTokenResult = throw NotImplementedError()

    }

    @Test
    @DisplayName("При отсутствии поддерживаемых платформ выбрасывается исключение")
    fun get_platformNotFound() {
        val pushMessageProviderServiceFactory = PushMessageProviderServiceFactoryImpl(
            pushMessageProviderProperties = PushMessageProviderProperties(
                platformProviders = emptyMap()
            ),
            pushMessageProviderServices = listOf(pushMessageProviderServiceFcm)
        )

        Assert.assertThrows(
            "Исключение не выбрасывается или принадлежит иному типу",
            CommonException::class.java
        ) {
            pushMessageProviderServiceFactory.get(PlatformType.IOS)
        }
    }

    @Test
    @DisplayName("При отсутствии назначенного провайдера у поддерживаемой платформы выбрасывается исключение")
    fun get_providerServiceForPlatformNotFound() {
        val pushMessageProviderServiceFactory = PushMessageProviderServiceFactoryImpl(
            pushMessageProviderProperties = PushMessageProviderProperties(
                platformProviders = mapOf(
                    PlatformType.IOS to emptyList()
                )
            ),
            pushMessageProviderServices = listOf(pushMessageProviderServiceFcm)
        )

        Assert.assertThrows(
            "Исключение не выбрасывается или принадлежит иному типу",
            CommonException::class.java
        ) {
            pushMessageProviderServiceFactory.get(PlatformType.IOS)
        }
    }

    @Test
    @DisplayName("Настроенной платформе назначается первый сервис из доступных")
    fun get_firstSupportedProviderService() {
        val pushMessageProviderServiceFactory = PushMessageProviderServiceFactoryImpl(
            pushMessageProviderProperties = PushMessageProviderProperties(
                platformProviders = mapOf(PlatformType.IOS to listOf(PushMessageProviderType.FCM))
            ),
            pushMessageProviderServices = listOf(
                pushMessageProviderServiceFcm,
                object : PushMessageProviderService {

                    override val type: PushMessageProviderType = PushMessageProviderType.FCM

                    override fun send(request: SendPushRequest): SendPushResult = throw NotImplementedError()

                    override fun check(request: PushTokenCheck): CheckPushTokenResult = throw NotImplementedError()

                }
            )
        )

        Assert.assertEquals(
            "Платформе назначен не первый сервис из доступных",
            pushMessageProviderServiceFcm,
            pushMessageProviderServiceFactory.get(PlatformType.IOS)
        )
    }


}
