package ru.touchin.push.message.provider.factories

import ru.touchin.push.message.provider.enums.PlatformType
import ru.touchin.push.message.provider.services.PushMessageProviderService

interface PushMessageProviderServiceFactory {

    fun get(platformType: PlatformType): PushMessageProviderService

}
