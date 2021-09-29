package ru.touchin.auth.core.user.services

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.ActiveProfiles
import ru.touchin.auth.core.device.converters.DeviceConverter.toDto
import ru.touchin.auth.core.device.dto.enums.DevicePlatform
import ru.touchin.auth.core.device.exceptions.DeviceAlreadyLinkedException
import ru.touchin.auth.core.device.models.DeviceEntity
import ru.touchin.auth.core.device.repository.DeviceRepository
import ru.touchin.auth.core.scope.dto.Scope
import ru.touchin.auth.core.scope.models.ScopeEntity
import ru.touchin.auth.core.scope.repositories.ScopeRepository
import ru.touchin.auth.core.user.dto.User
import ru.touchin.auth.core.user.dto.enums.IdentifierType
import ru.touchin.auth.core.user.models.UserEntity
import ru.touchin.auth.core.user.repositories.UserAccountRepository
import ru.touchin.auth.core.user.repositories.UserRepository
import ru.touchin.auth.core.user.services.dto.NewAnonymousUser
import ru.touchin.auth.core.user.services.dto.NewUser
import java.util.*

@ActiveProfiles("test")
@SpringBootTest
internal class UserCoreServiceImplTest {

    private var userRepository: UserRepository = mock {}
    private var userAccountRepository: UserAccountRepository = mock {}
    private var deviceRepository: DeviceRepository = mock {}
    private var scopeRepository: ScopeRepository = mock {}

    private var userCoreService: UserCoreService = spy(UserCoreServiceImpl(
        userRepository = userRepository,
        userAccountRepository = userAccountRepository,
        deviceRepository = deviceRepository,
        scopeRepository = scopeRepository,
        passwordEncoder = BCryptPasswordEncoder(),
    ))

    private val deviceWithoutUser = DeviceEntity().apply {
        id = UUID.fromString("0d3ded83-02c7-4bae-9cd2-3076e0bce046")
        platform = DevicePlatform.Android
        users = emptySet()
    }

    private val deviceWithAnonymousUser = DeviceEntity().apply {
        id = UUID.fromString("1ba666de-a0c9-4656-900c-864ebf1c0640")
        platform = DevicePlatform.Huawei
        users = hashSetOf(UserEntity())
    }

    private val deviceWithUser = DeviceEntity().apply {
        id = UUID.fromString("193061cd-b121-414f-952f-426eea3d9be2")
        platform = DevicePlatform.Apple
        users = hashSetOf(UserEntity().apply {
            anonymous = false
        })
    }

    private val defaultUserId = UUID.fromString("0daacbb9-74cd-4c98-bdbd-584dfcf5d342")

    @BeforeEach
    fun prepareMocks() {
        doReturn(
            deviceWithoutUser
        ).`when`(deviceRepository).findByIdWithLock(deviceWithoutUser.id!!)

        doReturn(
            deviceWithAnonymousUser
        ).`when`(deviceRepository).findByIdWithLock(deviceWithAnonymousUser.id!!)

        doReturn(
            deviceWithUser
        ).`when`(deviceRepository).findByIdWithLock(deviceWithUser.id!!)

        doAnswer { invocation ->
            (invocation.getArgument(0) as UserEntity)
                .apply {
                    id = defaultUserId
                }
        }.`when`(userRepository).save(any())
    }

    @Test
    @DisplayName("Должен создаваться анонимный пользователь")
    fun anonymousUserShouldBeCreated() {
        val actualUser = userCoreService.create(
            NewAnonymousUser(
                deviceId = deviceWithoutUser.id!!
            )
        )

        val expectedUser = User(
            id = defaultUserId,
            device = deviceWithoutUser.toDto(),
            scopes = emptySet()
        )

        assertEquals(expectedUser, actualUser)
    }

    @Test
    @DisplayName("Новый анонимный пользователь не может быть привязан к уже занятому устройству")
    fun shouldBeDeviceAlreadyLinkedException() {
        assertThrows<DeviceAlreadyLinkedException> {
            userCoreService.create(
                NewAnonymousUser(
                    deviceId = deviceWithAnonymousUser.id!!
                )
            )
        }
    }

    @Test
    @DisplayName("У анонимного пользователя должен быть флаг anonymous=true и confirmed=false")
    fun shouldBeAnonymousFlags() {
        userCoreService.create(
            NewAnonymousUser(
                deviceId = deviceWithoutUser.id!!,
            )
        )

        verify(userRepository).save(argThat { anonymous })
        verify(userRepository).save(argThat { confirmedAt == null })
    }

    @Test
    @DisplayName("У обычного пользователя должен быть флаг anonymous=false и confirmed=false")
    fun shouldNotBeAnonymousFlags() {
        userCoreService.create(
            NewUser(
                userId = null,
                deviceId = deviceWithoutUser.id!!,
                username = "user",
                password = null,
                identifierType = IdentifierType.Username,
            )
        )

        verify(userRepository).save(argThat { !anonymous })
        verify(userRepository).save(argThat { confirmedAt == null })
    }

    @Test
    @DisplayName("Должен создаваться обычный пользователь")
    fun userShouldBeCreated() {
        doReturn(
            listOf(
                ScopeEntity().apply {
                    name = "app:api"
                }
            )
        ).`when`(scopeRepository).findByGroup(any())

        val actualUser = userCoreService.create(
            NewUser(
                userId = null,
                deviceId = deviceWithoutUser.id!!,
                username = "admin",
                password = "foo",
                identifierType = IdentifierType.PhoneNumber,
            )
        )

        val expectedUser = User(
            id = defaultUserId,
            device = deviceWithoutUser.toDto(),
            scopes = setOf(Scope("app:api"))
        )

        assertEquals(expectedUser, actualUser)
    }

}
