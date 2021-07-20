package ru.touchin.auth.core.tokens.refresh.services

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.auth.core.device.converters.DeviceConverter.toDto
import ru.touchin.auth.core.device.repository.DeviceRepository
import ru.touchin.auth.core.scope.dto.Scope
import ru.touchin.auth.core.scope.repositories.ScopeRepository
import ru.touchin.auth.core.tokens.refresh.dto.RefreshToken
import ru.touchin.auth.core.tokens.refresh.models.RefreshTokenEntity
import ru.touchin.auth.core.tokens.refresh.properties.RefreshTokenProperties
import ru.touchin.auth.core.tokens.refresh.repositories.RefreshTokenRepository
import ru.touchin.auth.core.tokens.refresh.repositories.findByValueOrThrow
import ru.touchin.auth.core.tokens.refresh.services.dto.NewRefreshToken
import ru.touchin.auth.core.user.converters.UserConverter.toDto
import ru.touchin.auth.core.user.repositories.UserRepository
import ru.touchin.auth.core.user.repositories.findByIdOrThrow
import ru.touchin.common.byte.ByteUtils.toHex
import ru.touchin.common.random.SecureRandomStringGenerator
import ru.touchin.common.security.hash.HashUtils
import ru.touchin.common.security.hash.HashUtils.calculateHash
import java.time.ZonedDateTime

@Service
class RefreshTokenCoreServiceImpl(
    private val refreshTokenProperties: RefreshTokenProperties,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userRepository: UserRepository,
    private val deviceRepository: DeviceRepository,
    private val scopeRepository: ScopeRepository,
) : RefreshTokenCoreService {

    @Transactional(readOnly = true)
    override fun get(value: String): RefreshToken {
        val valueHash = getTokenHash(value)

        return refreshTokenRepository.findByValueOrThrow(valueHash)
            .toDto(value)
    }

    @Transactional
    override fun create(token: NewRefreshToken): RefreshToken {
        val user = userRepository.findByIdOrThrow(token.userId)
        val device = token.deviceId?.let(deviceRepository::findByIdOrNull)
        val scopes = scopeRepository.findAllById(token.scopes.map(Scope::name))
        val value = generateTokenValue()

        val model = RefreshTokenEntity().apply {
            expiresAt = getExpirationDate()
            this.value = getTokenHash(value)
            this.user = user
            this.device = device
            this.scopes = scopes.toSet()
        }

        return refreshTokenRepository.save(model)
            .toDto(value)
    }

    @Transactional
    override fun refresh(value: String): RefreshToken {
        val valueHash = getTokenHash(value)

        val oldToken = refreshTokenRepository.findByValueOrThrow(valueHash)
            .validate()
            .apply {
                usedAt = ZonedDateTime.now()
            }

        refreshTokenRepository.save(oldToken)

        val newValue = generateTokenValue()

        val model = RefreshTokenEntity().apply {
            this.value = getTokenHash(newValue)
            expiresAt = getExpirationDate()
            user = oldToken.user
            device = oldToken.device
            scopes = oldToken.scopes.toSet()
        }

        return refreshTokenRepository.save(model)
            .toDto(newValue)
    }

    private fun getExpirationDate(): ZonedDateTime {
        return ZonedDateTime.now().plus(refreshTokenProperties.timeToLive)
    }

    private fun generateTokenValue(): String {
        return refreshTokenProperties.let {
            it.prefix + SecureRandomStringGenerator.generate(it.length)
        }
    }

    private fun getTokenHash(value: String): String {
        return value.calculateHash(HashUtils.HashAlgorithm.MD5)
            .toHex()
    }

    companion object {

        fun RefreshTokenEntity.toDto(rawValue: String): RefreshToken {
            val device = device?.toDto()

            return RefreshToken(
                value = rawValue,
                expiresAt = expiresAt,
                usedAt = usedAt,
                user = user.toDto(device)
            )
        }
    }

}
