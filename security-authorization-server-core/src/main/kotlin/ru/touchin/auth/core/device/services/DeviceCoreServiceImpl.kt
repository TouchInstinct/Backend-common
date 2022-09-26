@file:Suppress("unused")
package ru.touchin.auth.core.device.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.auth.core.device.converters.DeviceConverter.toDto
import ru.touchin.auth.core.device.dto.Device
import ru.touchin.auth.core.device.models.DeviceEntity
import ru.touchin.auth.core.device.repository.DeviceRepository
import ru.touchin.auth.core.device.repository.findByIdOrThrow
import ru.touchin.common.devices.enums.DevicePlatform
import java.util.*

@Service
class DeviceCoreServiceImpl(
    private val deviceRepository: DeviceRepository,
) : DeviceCoreService {


    @Transactional(readOnly = true)
    override fun get(deviceId: UUID): Device {
        return deviceRepository.findByIdOrThrow(deviceId)
            .toDto()
    }

    @Transactional
    override fun create(platform: DevicePlatform): Device {
        return deviceRepository.save(DeviceEntity.create(platform))
            .toDto()
    }

}
