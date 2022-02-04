package ru.touchin.auth.core.user.repositories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import ru.touchin.auth.core.device.dto.enums.DevicePlatform
import ru.touchin.auth.core.device.models.DeviceEntity
import ru.touchin.auth.core.device.repository.DeviceRepository
import ru.touchin.auth.core.scope.models.ScopeEntity
import ru.touchin.auth.core.scope.repositories.ScopeRepository
import ru.touchin.auth.core.user.exceptions.UserNotFoundException
import ru.touchin.auth.core.user.models.UserEntity
import ru.touchin.common.spring.test.jpa.repository.RepositoryTest
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.EntityManager

@RepositoryTest
internal class UserRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var deviceRepository: DeviceRepository

    @Autowired
    private lateinit var scopeRepository: ScopeRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @DisplayName("Можно создать `UserModel` без привязки к устройствам или скоупам")
    fun userShouldBeCreated() {
        val user = userRepository.saveAndFlush(
            UserEntity().apply {
                anonymous = false
                confirmedAt = ZonedDateTime.now()
                devices = emptySet()
                scopes = mutableSetOf()
            }
        )

        entityManager.clear()

        val savedUser = userRepository.findByIdOrThrow(user.id!!)

        assertTrue(ReflectionEquals(user, "createdAt").matches(savedUser))

    }

    @Test
    @DisplayName("Можно привязать к пользователю существующий device")
    fun canBindDevice() {
        val device = deviceRepository.saveAndFlush(
            DeviceEntity.create(DevicePlatform.Apple)
        )

        val user = UserEntity()
            .apply {
                devices = setOf(device)
            }

        userRepository.saveAndFlush(user)

        entityManager.clear()

        val savedUser = userRepository.findByIdOrThrow(user.id!!)

        assertEquals(1, savedUser.devices.size)
    }

    @Test
    @DisplayName("Можно привязать к пользователю существующий scope")
    fun canBindScope() {
        val scope = scopeRepository.saveAndFlush(
            ScopeEntity().apply {
                name = "admin"
            }
        )

        val user = UserEntity()
            .apply {
                scopes = mutableSetOf(scope)
            }

        userRepository.saveAndFlush(user)

        entityManager.clear()

        val savedUser = userRepository.findByIdOrThrow(user.id!!)

        assertEquals(1, savedUser.scopes.size)
    }

    @Test
    @DisplayName("Должна быть ошибка при сохранении пользователя с новым устройством")
    fun shouldBeErrorIfDeviceNew() {
        val user = UserEntity()
            .apply {
                devices = setOf(DeviceEntity.create(DevicePlatform.Huawei))
            }

        assertThrows(DataAccessException::class.java) {
            userRepository.saveAndFlush(user)
        }
    }

    @Test
    @DisplayName("Должна быть ошибка при сохранении пользователя с новым scope")
    fun shouldBeErrorIfScopeNew() {
        val user = UserEntity()
            .apply {
                scopes = mutableSetOf(ScopeEntity().apply { name = "admin" })
            }

        assertThrows(DataAccessException::class.java) {
            userRepository.saveAndFlush(user)
        }
    }

    @Test
    @DisplayName("Если пользователь не найден, то должна быть ошибка UserNotFoundException")
    fun shouldBeUserNotFoundException() {
        val missingUserId = UUID.fromString("d3e957df-6686-421e-9807-7128c8e680ea")

        assertThrows(UserNotFoundException::class.java) {
            userRepository.findByIdOrThrow(missingUserId)
        }
    }

}
