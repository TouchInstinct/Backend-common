package ru.touchin.auth.core.device.exceptions

import ru.touchin.common.exceptions.CommonException
import java.util.*

class DeviceAlreadyLinkedException(deviceId: UUID) : CommonException(
    "Device $deviceId already linked to other user"
)
