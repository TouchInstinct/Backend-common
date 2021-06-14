package ru.touchin.auth.core.device.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.findByIdOrNull
import ru.touchin.auth.core.device.exceptions.DeviceNotFoundException
import ru.touchin.auth.core.device.models.DeviceEntity
import java.util.*
import javax.persistence.LockModeType

interface DeviceRepository: JpaRepository<DeviceEntity, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT d FROM DeviceEntity d WHERE d.id = :deviceId")
    fun findByIdWithLock(deviceId: UUID): DeviceEntity?

}

fun DeviceRepository.findByIdOrThrow(deviceId: UUID): DeviceEntity {
    return findByIdOrNull(deviceId)
        ?: throw DeviceNotFoundException(deviceId)
}

fun DeviceRepository.findByIdWithLockOrThrow(deviceId: UUID): DeviceEntity {
    return findByIdWithLock(deviceId)
        ?: throw DeviceNotFoundException(deviceId)
}
