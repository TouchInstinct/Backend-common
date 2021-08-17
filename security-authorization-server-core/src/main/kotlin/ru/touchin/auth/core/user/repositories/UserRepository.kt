package ru.touchin.auth.core.user.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import ru.touchin.auth.core.user.exceptions.UserNotFoundException
import ru.touchin.auth.core.user.models.UserEntity
import java.util.*

interface UserRepository: JpaRepository<UserEntity, UUID>

fun UserRepository.findByIdOrThrow(userId: UUID): UserEntity {
    return findByIdOrNull(userId)
        ?: throw UserNotFoundException(userId)
}
