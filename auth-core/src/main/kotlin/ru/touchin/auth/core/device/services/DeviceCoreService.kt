package ru.touchin.auth.core.device.services

import ru.touchin.auth.core.device.dto.Device
import ru.touchin.auth.core.device.dto.enums.DevicePlatform
import java.util.UUID

interface DeviceCoreService {

    fun get(deviceId: UUID): Device
    fun create(platform: DevicePlatform): Device

}
