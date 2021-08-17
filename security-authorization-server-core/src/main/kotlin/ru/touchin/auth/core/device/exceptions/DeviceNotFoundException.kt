package ru.touchin.auth.core.device.exceptions

import ru.touchin.common.exceptions.CommonNotFoundException
import java.util.*

class DeviceNotFoundException(deviceId: UUID) : CommonNotFoundException(
    "Device not found id=$deviceId"
)
