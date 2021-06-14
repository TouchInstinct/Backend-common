package ru.touchin.auth.core.tokens.refresh.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.touchin.auth.core.tokens.refresh.exceptions.RefreshTokenNotFoundException
import ru.touchin.auth.core.tokens.refresh.models.RefreshTokenEntity

interface RefreshTokenRepository : JpaRepository<RefreshTokenEntity, Long> {

    fun findByValue(value: String): RefreshTokenEntity?

}

fun RefreshTokenRepository.findByValueOrThrow(value: String): RefreshTokenEntity {
    return findByValue(value)
        ?: throw RefreshTokenNotFoundException(value)
}
