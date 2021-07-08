@file:Suppress("unused")

package ru.touchin.auth.core.user.services

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.auth.core.device.converters.DeviceConverter.toDto
import ru.touchin.auth.core.device.exceptions.DeviceAlreadyLinkedException
import ru.touchin.auth.core.device.models.DeviceEntity
import ru.touchin.auth.core.device.repository.DeviceRepository
import ru.touchin.auth.core.device.repository.findByIdWithLockOrThrow
import ru.touchin.auth.core.scope.models.ScopeGroupEntity
import ru.touchin.auth.core.scope.repositories.ScopeRepository
import ru.touchin.auth.core.user.converters.UserAccountConverter.toDto
import ru.touchin.auth.core.user.converters.UserConverter.toDto
import ru.touchin.auth.core.user.dto.User
import ru.touchin.auth.core.user.dto.UserAccount
import ru.touchin.auth.core.user.dto.enums.IdentifierType
import ru.touchin.auth.core.user.exceptions.UserAccountNotFoundException
import ru.touchin.auth.core.user.exceptions.UserAlreadyRegisteredException
import ru.touchin.auth.core.user.exceptions.WrongPasswordException
import ru.touchin.auth.core.user.models.UserAccountEntity
import ru.touchin.auth.core.user.models.UserEntity
import ru.touchin.auth.core.user.repositories.UserAccountRepository
import ru.touchin.auth.core.user.repositories.UserRepository
import ru.touchin.auth.core.user.repositories.findByIdOrThrow
import ru.touchin.auth.core.user.repositories.findByUserIdOrThrow
import ru.touchin.auth.core.user.repositories.findByUsernameOrThrow
import ru.touchin.auth.core.user.services.dto.GetUserAccount
import ru.touchin.auth.core.user.services.dto.NewAnonymousUser
import ru.touchin.auth.core.user.services.dto.NewUser
import ru.touchin.auth.core.user.services.dto.UserLogin
import ru.touchin.auth.core.user.services.dto.UserLogout

@Service
class UserCoreServiceImpl(
    private val userRepository: UserRepository,
    private val userAccountRepository: UserAccountRepository,
    private val deviceRepository: DeviceRepository,
    private val scopeRepository: ScopeRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserCoreService {

    @Transactional
    override fun create(newAnonymousUser: NewAnonymousUser): User {
        val device = deviceRepository.findByIdWithLockOrThrow(newAnonymousUser.deviceId)

        if (device.users.isNotEmpty()) {
            throw DeviceAlreadyLinkedException(newAnonymousUser.deviceId)
        }

        val user = UserEntity().apply {
            anonymous = true
            devices = hashSetOf(device)
            scopes = emptySet()
        }

        return userRepository.save(user)
            .toDto(device.toDto())
    }

    @Transactional
    override fun create(newUser: NewUser): User {
        userAccountRepository.findByUsername(newUser.username, newUser.identifierType)
            ?.run { throw UserAlreadyRegisteredException(newUser.username) }

        val device = deviceRepository.findByIdWithLockOrThrow(newUser.deviceId)

        resetDeviceUsers(device)

        val defaultScopes = scopeRepository.findByGroup(ScopeGroupEntity.DEFAULT_USER_SCOPE_GROUP)

        val user = UserEntity()
            .apply {
                anonymous = false
                devices = hashSetOf(device)
                scopes = defaultScopes.toSet()
            }
            .also(userRepository::save)

        UserAccountEntity()
            .apply {
                username = newUser.username
                password = newUser.password?.let(passwordEncoder::encode)
                identifierType = newUser.identifierType
                this.user = user
            }
            .also(userAccountRepository::save)

        return user.toDto(device.toDto())
    }

    @Transactional
    override fun login(userLogin: UserLogin): User {
        val device = deviceRepository.findByIdWithLockOrThrow(userLogin.deviceId)

        val userAccount = userAccountRepository.findByUsernameOrThrow(userLogin.username, userLogin.identifierType)

        if (userAccount.password != null) {
            if (!passwordEncoder.matches(userLogin.password, userAccount.password!!)) {
                throw WrongPasswordException(userLogin.username)
            }
        }

        resetDeviceUsers(device)

        val user = userAccount.user
            .apply {
                devices = hashSetOf(device)
            }
            .also(userRepository::save)

        return user.toDto(device.toDto())
    }

    @Transactional
    override fun logout(userLogout: UserLogout) {
        val device = deviceRepository.findByIdWithLockOrThrow(userLogout.deviceId)

        resetDeviceUsers(device)

        userRepository.findByIdOrThrow(userLogout.userId)
            .apply {
                devices = hashSetOf()
            }
            .also(userRepository::save)
    }

    @Transactional(readOnly = true)
    override fun get(username: String, identifierType: IdentifierType): User {
        return getOrNull(username, identifierType)
            ?: throw UserAccountNotFoundException(username)
    }

    @Transactional(readOnly = true)
    override fun getUserAccount(userAccount: GetUserAccount): UserAccount {
        return userAccountRepository.findByUserIdOrThrow(userAccount.userId, userAccount.identifierType)
            .toDto()
    }

    @Transactional(readOnly = true)
    override fun getOrNull(username: String, identifierType: IdentifierType): User? {
        return userAccountRepository.findByUsername(username, identifierType)
            ?.let { userAccount ->
                val user = userAccount.user

                user.toDto(device = null)
            }
    }

    private fun resetDeviceUsers(device: DeviceEntity) {
        deviceRepository.save(device.apply {
            users = hashSetOf()
        })
    }

}
