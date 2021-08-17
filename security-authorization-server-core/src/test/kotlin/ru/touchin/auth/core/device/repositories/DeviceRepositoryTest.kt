package ru.touchin.auth.core.device.repositories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import ru.touchin.auth.core.device.exceptions.DeviceNotFoundException
import ru.touchin.auth.core.device.models.DeviceEntity
import ru.touchin.auth.core.device.dto.enums.DevicePlatform
import ru.touchin.auth.core.device.repository.DeviceRepository
import ru.touchin.auth.core.device.repository.findByIdOrThrow
import ru.touchin.auth.core.device.repository.findByIdWithLockOrThrow
import ru.touchin.auth.core.user.models.UserEntity
import ru.touchin.auth.core.user.repositories.UserRepository
import ru.touchin.common.spring.test.jpa.repository.RepositoryTest
import java.time.Duration
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.persistence.EntityManager

@RepositoryTest
internal class DeviceRepositoryTest {

    @Autowired
    private lateinit var deviceLockService: DeviceLockService

    @Autowired
    private lateinit var deviceRepository: DeviceRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    fun createDevice(platform: DevicePlatform): DeviceEntity {
        return createDevice(DeviceEntity.create(platform))
    }

    fun createDevice(deviceModel: DeviceEntity): DeviceEntity {
        return deviceRepository.saveAndFlush(deviceModel)
    }

    @Test
    @DisplayName("Можно создать `DeviceModel` для всех платформ без привязки к пользователю")
    fun deviceShouldBeCreated() {
        DevicePlatform.values().forEach(::createDevice)

        entityManager.clear()

        val createdPlatforms = deviceRepository.findAll()
            .map(DeviceEntity::platform)

        assertEquals(
            DevicePlatform.values().size,
            createdPlatforms.size,
            "Кол-во записей в базе должно совпадать с кол-вом платформ"
        )

        assertEquals(
            DevicePlatform.values().toSet(),
            createdPlatforms.toSet(),
            "Платформы в базе должны совпадать со всеми `DevicePlatform`"
        )
    }

    @Test
    @DisplayName("Можно привязать существующего пользователя")
    fun canBindUser() {
        val user = userRepository.save(UserEntity())

        val device = DeviceEntity.create(DevicePlatform.Apple)
            .apply {
                users = setOf(user)
            }

        deviceRepository.saveAndFlush(device)

        entityManager.clear()

        val savedDevice = deviceRepository.findByIdOrThrow(device.id!!)

        assertEquals(1, savedDevice.users.size)
    }

    @Test
    @DisplayName("Должна быть ошибка при сохранении устройства с новым пользователем")
    fun deviceShouldNotBeCreatedWithNewUser() {
        val device = DeviceEntity.create(DevicePlatform.Android)
            .apply {
                users = setOf(UserEntity())
            }

        assertThrows(DataAccessException::class.java) {
            deviceRepository.saveAndFlush(device)
        }
    }

    @Test
    @DisplayName("Если девайс не найден, то должна быть ошибка DeviceNotFoundException")
    fun shouldBeDeviceNotFoundException() {
        val missingDeviceId = UUID.fromString("d3e957df-6686-421e-9807-7128c8e680ea")

        assertThrows(DeviceNotFoundException::class.java) {
            deviceRepository.findByIdOrThrow(missingDeviceId)
        }

        assertThrows(DeviceNotFoundException::class.java) {
            deviceRepository.findByIdWithLockOrThrow(missingDeviceId)
        }
    }

    @Test
    @DisplayName("Должен работать Lock")
    fun shouldBeLockable() {
        val device = deviceLockService.create {
            createDevice(DevicePlatform.Huawei)
        }

        val threadsCount = 2

        val service = Executors.newFixedThreadPool(threadsCount)
        val latch = CountDownLatch(threadsCount)

        val seq = ConcurrentLinkedQueue<Int>()

        service.submit {
            deviceLockService.getWithLock(id = device.id!!, Duration.ofSeconds(1))
            latch.countDown()
            seq.add(1)
        }

        Thread.sleep(50)

        service.submit {
            deviceLockService.getWithLock(id = device.id!!, Duration.ofSeconds(0))
            latch.countDown()
            seq.add(2)
        }

        latch.await(3, TimeUnit.SECONDS)

        deviceLockService.cleanup()

        assertEquals(1, seq.poll())
        assertEquals(2, seq.poll())

    }

}
