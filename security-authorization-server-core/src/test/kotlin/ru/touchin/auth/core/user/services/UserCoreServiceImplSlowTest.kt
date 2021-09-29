package ru.touchin.auth.core.user.services

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import ru.touchin.auth.core.device.dto.Device
import ru.touchin.auth.core.device.dto.enums.DevicePlatform
import ru.touchin.auth.core.device.repository.DeviceRepository
import ru.touchin.auth.core.device.repository.findByIdOrThrow
import ru.touchin.auth.core.device.services.DeviceCoreService
import ru.touchin.auth.core.user.dto.User
import ru.touchin.auth.core.user.dto.enums.IdentifierType
import ru.touchin.auth.core.user.exceptions.UserAlreadyRegisteredException
import ru.touchin.auth.core.user.exceptions.WrongPasswordException
import ru.touchin.auth.core.user.repositories.UserAccountRepository
import ru.touchin.auth.core.user.repositories.UserRepository
import ru.touchin.auth.core.user.repositories.findByIdOrThrow
import ru.touchin.auth.core.user.services.dto.NewAnonymousUser
import ru.touchin.auth.core.user.services.dto.NewUser
import ru.touchin.auth.core.user.services.dto.UserLogin
import ru.touchin.auth.core.user.services.dto.UserLogout
import ru.touchin.auth.core.user.services.dto.UserUpdatePassword
import ru.touchin.common.spring.test.jpa.repository.RepositoryTest
import java.lang.IllegalArgumentException
import java.util.*
import javax.persistence.EntityManager

@RepositoryTest
internal class UserCoreServiceImplSlowTest {

    @Autowired
    lateinit var userCoreService: UserCoreService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var deviceCoreService: DeviceCoreService

    @Autowired
    lateinit var deviceRepository: DeviceRepository

    @Autowired
    lateinit var userAccountRepository: UserAccountRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    private fun createDevice(): Device {
        return deviceCoreService.create(DevicePlatform.Android).also {
            entityManager.flush()
            entityManager.clear()
        }
    }

    private fun createNewUser(deviceId: UUID, password: String? = null, username: String = "manager"): User {
        val newUser = NewUser(
            userId = null,
            deviceId = deviceId,
            username = username,
            password = password,
            identifierType = IdentifierType.Email,
        )

        return userCoreService.create(newUser).also {
            entityManager.flush()
            entityManager.clear()
        }
    }

    @Test
    @DisplayName("Username должен быть уникальным для одного и того же IdentifierType")
    fun usernameShouldBeUniq() {
        val device1 = createDevice()

        createNewUser(device1.id, password = null)

        val device2 = createDevice()

        assertThrows<UserAlreadyRegisteredException> {
            createNewUser(device2.id, password = "qwerty")
        }

        assertThrows<UserAlreadyRegisteredException> {
            createNewUser(
                deviceId = UUID.fromString("2ecc93c9-fccb-4cc9-8d69-18a39b83e707"),
                password = "qwerty"
            )
        }
    }

    @Test
    @DisplayName("""
       Анонимные пользователи должны отлинковываться от устройства на которое регистрируется обычный пользователь
    """)
    fun anonymousUsersShouldBeUnbinded() {
        val device = createDevice()

        val anonymousUser = userCoreService.create(
            NewAnonymousUser(device.id)
        ).also {
            entityManager.flush()
            entityManager.clear()
        }

        val anonymousUserEntity = userRepository.findByIdOrThrow(anonymousUser.id)

        assertEquals(1, anonymousUserEntity.devices.size)
        assertEquals(device.id, anonymousUserEntity.devices.first().id!!)

        val user = createNewUser(device.id)

        val deviceEntity = deviceRepository.findByIdOrThrow(device.id)

        assertEquals(1, deviceEntity.users.size)
        assertEquals(user.id, deviceEntity.users.first().id!!)

    }

    @Test
    @DisplayName("""
       Обычные пользователи должны отлинковываться от устройства на которое регистрируется обычный пользователь
    """)
    fun usersShouldBeUnbinded() {
        val device = createDevice()

        val apiUser = createNewUser(device.id, username = "user@gmail.com")

        val anonymousUserEntity = userRepository.findByIdOrThrow(apiUser.id)

        assertEquals(1, anonymousUserEntity.devices.size)
        assertEquals(device.id, anonymousUserEntity.devices.first().id!!)

        val user = createNewUser(device.id, username = "manager@gmail.com")

        val deviceEntity = deviceRepository.findByIdOrThrow(device.id)

        assertEquals(1, deviceEntity.users.size)
        assertEquals(user.id, deviceEntity.users.first().id!!)

    }

    @Test
    @DisplayName("""
        При регистрации пользователя, должен создаваться аккаунт в базе
    """)
    fun accountShouldBeCreated() {
        val device = createDevice()

        val newUser = NewUser(
            userId = null,
            deviceId = device.id,
            username = "+71232322023",
            password = "qwerty",
            identifierType = IdentifierType.PhoneNumber,
        )

        userCoreService.create(newUser).also {
            entityManager.flush()
            entityManager.clear()
        }

        val account = userAccountRepository.findByUsername(newUser.username, IdentifierType.PhoneNumber)

        assertNotNull(account)
        assertEquals(newUser.username, account!!.username)
        assertTrue(passwordEncoder.matches(newUser.password!!, account.password!!))
        assertEquals(newUser.identifierType, account.identifierType)

    }

    @Test
    @DisplayName("Должен быть успешный логин с паролем")
    fun loginShouldBeOk() {
        val device = createDevice()

        val regUser = createNewUser(deviceId = device.id, password = "qwerty", username = "employee")

        val logingUser = userCoreService.login(
            UserLogin(
                deviceId = device.id,
                username = "employee",
                password = "qwerty",
                identifierType = IdentifierType.Email,
            )
        )

        assertEquals(regUser, logingUser)
    }

    @Test
    @DisplayName("Пустой пароль должен подходить")
    fun emptyPassLoginShouldBeOk() {
        val device = createDevice()

        createNewUser(deviceId = device.id, password = null, username = "employee")

        assertDoesNotThrow {
            userCoreService.login(
                UserLogin(
                    deviceId = device.id,
                    username = "employee",
                    password = null,
                    identifierType = IdentifierType.Email,
                )
            )
        }
    }

    @Test
    @DisplayName("Пустой пароль не должен подходиить")
    fun emptyPassLoginShouldBeFail() {
        val device = createDevice()

        createNewUser(deviceId = device.id, password = "qwerty", username = "employee")

        assertThrows<IllegalArgumentException> {
            userCoreService.login(
                UserLogin(
                    deviceId = device.id,
                    username = "employee",
                    password = null,
                    identifierType = IdentifierType.Email,
                )
            )
        }
    }

    @Test
    @DisplayName("Если пароль не подходит, то WrongPasswordException")
    fun shouldBeWrongPasswordException() {
        val device = createDevice()

        createNewUser(deviceId = device.id, password = "qwerty", username = "employee")

        assertThrows<WrongPasswordException> {
            userCoreService.login(
                UserLogin(
                    deviceId = device.id,
                    username = "employee",
                    password = "qwerTy",
                    identifierType = IdentifierType.Email,
                )
            )
        }
    }

    @Test
    @DisplayName("При логине старые пользователи должны отлинковываться от устройства")
    fun prevUsersShouldBeUnbindedAfterLogin() {
        val device = createDevice()

        createNewUser(deviceId = device.id, password = "qwerty", username = "employee1")
        createNewUser(deviceId = device.id, password = "qwerty", username = "employee2")
        val regUserE3 = createNewUser(deviceId = device.id, password = "qwerty", username = "employee3")

        val actualDeviceE3 = deviceRepository.findByIdOrThrow(deviceId = device.id)

        assertEquals(1, actualDeviceE3.users.size)
        assertEquals(regUserE3.id, actualDeviceE3.users.first().id!!)

        val loginUserE2 = userCoreService.login(
            UserLogin(
                deviceId = device.id,
                username = "employee2",
                password = "qwerty",
                identifierType = IdentifierType.Email,
            )
        ).also {
            entityManager.flush()
            entityManager.clear()
        }

        val actualDeviceE2 = deviceRepository.findByIdOrThrow(deviceId = device.id)

        assertEquals(1, actualDeviceE2.users.size)
        assertEquals(loginUserE2.id, actualDeviceE2.users.first().id!!)
    }

    @Test
    @DisplayName("При логауте пользователь отлинковывается от устройства")
    fun usersShouldBeUnbindedAfterLogout() {
        val device = createDevice()

        createNewUser(deviceId = device.id, password = "qwerty", username = "employee1")
        val regUserE2 = createNewUser(deviceId = device.id, password = "qwerty", username = "employee2")

        val actualDeviceE2 = deviceRepository.findByIdOrThrow(deviceId = device.id)

        assertEquals(1, actualDeviceE2.users.size)
        assertEquals(regUserE2.id, actualDeviceE2.users.first().id!!)

        userCoreService.logout(
            UserLogout(
                deviceId = device.id,
                userId = regUserE2.id
            )
        ).also {
            entityManager.flush()
            entityManager.clear()
        }

        val actualDevice = deviceRepository.findByIdOrThrow(deviceId = device.id)

        assertTrue(actualDevice.users.isEmpty())
    }

    @Test
    @DisplayName("Пользователь может залогиниться на нескольких устройствах")
    fun usersShouldOkTwoDevicesLogin() {
        val deviceD1 = createDevice()
        val deviceD2 = createDevice()

        val regUser = createNewUser(deviceId = deviceD1.id, password = "qwerty", username = "employee")

        userCoreService.login(
            UserLogin(
                deviceId = deviceD2.id,
                username = "employee",
                password = "qwerty",
                identifierType = IdentifierType.Email,
            )
        ).also {
            entityManager.flush()
            entityManager.clear()
        }

        val actualUser = userRepository.findByIdOrThrow(regUser.id)

        assertEquals(2, actualUser.devices.size)

        userCoreService.logout(
            UserLogout(
                deviceId = deviceD1.id,
                userId = regUser.id
            )
        ).also {
            entityManager.flush()
            entityManager.clear()
        }

        val actualUser2 = userRepository.findByIdOrThrow(regUser.id)

        assertEquals(1, actualUser2.devices.size)
        assertEquals(deviceD2.id, actualUser2.devices.first().id!!)
    }

    @Test
    @DisplayName("Пользователь может сменить пароль")
    fun userCanChangePassword() {
        val device = createDevice()

        val regUser = createNewUser(deviceId = device.id, password = "qwerty", username = "employee1")

        val userAccount = userAccountRepository.findByUserId(regUser.id, IdentifierType.Email)

        assertNotNull(userAccount)

        userCoreService.updatePassword(
            UserUpdatePassword(
                userAccountId = userAccount?.id!!,
                oldPassword = "qwerty",
                newPassword = "QWERTY1234"
            )
        ).also {
            entityManager.flush()
            entityManager.clear()
        }

        val actualUserAccount = userAccountRepository.findByUsername("employee1", IdentifierType.Email)

        assertNotNull(actualUserAccount)
        assertFalse(passwordEncoder.matches(userAccount.password!!, actualUserAccount?.password!!))
        assertTrue(passwordEncoder.matches("QWERTY1234", actualUserAccount.password!!))
    }

}
