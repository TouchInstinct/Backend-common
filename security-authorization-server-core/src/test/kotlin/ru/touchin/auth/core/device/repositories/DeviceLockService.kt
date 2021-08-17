package ru.touchin.auth.core.device.repositories

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.touchin.auth.core.device.models.DeviceEntity
import ru.touchin.auth.core.device.repository.DeviceRepository
import ru.touchin.auth.core.device.repository.findByIdWithLockOrThrow
import java.time.Duration
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
class DeviceLockService {

    @Autowired
    private lateinit var deviceRepository: DeviceRepository

    fun getWithLock(id: UUID, pauseAfter: Duration): DeviceEntity? {
        val device = deviceRepository.findByIdWithLockOrThrow(id)

        Thread.sleep(pauseAfter.toMillis())

        return device
    }

    fun create(f: () -> DeviceEntity): DeviceEntity {
        return f.invoke()
    }

    fun cleanup() {
        deviceRepository.deleteAll()
    }

}
