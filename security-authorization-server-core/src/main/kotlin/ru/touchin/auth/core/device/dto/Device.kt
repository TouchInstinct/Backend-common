package ru.touchin.auth.core.device.dto

import ru.touchin.common.devices.enums.DevicePlatform
import java.util.*

data class Device(
    val id: UUID,
    val platform: DevicePlatform,
)
