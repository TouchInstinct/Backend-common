package ru.touchin.auth.core.device.converters

import ru.touchin.auth.core.device.dto.Device
import ru.touchin.auth.core.device.models.DeviceEntity

object DeviceConverter {

    fun DeviceEntity.toDto(): Device {
        return Device(
            id = this.id!!,
            platform = this.platform,
        )
    }

}
