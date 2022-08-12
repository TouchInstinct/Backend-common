package ru.touchin.push.message.provider.factories

import org.springframework.stereotype.Component
import ru.touchin.common.exceptions.CommonException
import ru.touchin.push.message.provider.enums.PlatformType
import ru.touchin.push.message.provider.properties.PushMessageProviderProperties
import ru.touchin.push.message.provider.services.PushMessageProviderService
import kotlin.jvm.Throws

@Component
class PushMessageProviderServiceFactoryImpl(
    private val pushMessageProviderProperties: PushMessageProviderProperties,
    private val pushMessageProviderServices: List<PushMessageProviderService>
) : PushMessageProviderServiceFactory {

    @Throws(CommonException::class)
    override fun get(platformType: PlatformType): PushMessageProviderService {
        val supportedProviderTypes = pushMessageProviderProperties.platformProviders[platformType]?.firstOrNull()
            ?: throw CommonException("Configuration has no setup for platform '$platformType'")

        return pushMessageProviderServices.find { it.type == supportedProviderTypes }
            ?: throw CommonException("Configuration has no push message provider support for platform '$platformType'")
    }

}
