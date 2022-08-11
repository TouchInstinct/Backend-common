package ru.touchin.push.message.provider.factories

import org.springframework.stereotype.Component
import ru.touchin.common.exceptions.CommonException
import ru.touchin.push.message.provider.enums.PlatformType
import ru.touchin.push.message.provider.properties.PushMessageProviderProperties
import ru.touchin.push.message.provider.services.PushMessageProviderService

@Component
class PushMessageProviderServiceFactory(
    private val pushMessageProviderProperties: PushMessageProviderProperties,
    private val pushMessageProviderServices: List<PushMessageProviderService>
) {

    fun get(platformType: PlatformType): PushMessageProviderService {
        val supportedProviderTypes = pushMessageProviderProperties.platformProviders[platformType]?.firstOrNull()
            ?: throw CommonException("No push message provider set for platform '$platformType'")

        return pushMessageProviderServices.find { it.type == supportedProviderTypes }
            ?: throw CommonException("No push message provider found for platform '$platformType'")
    }

}
