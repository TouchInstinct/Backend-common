@file:Suppress("unused")

package ru.touchin.auth.core.user.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.touchin.auth.core.user.dto.enums.IdentifierType
import ru.touchin.auth.core.user.exceptions.UserAccountNotFoundException
import ru.touchin.auth.core.user.models.UserAccountEntity
import java.util.*

interface UserAccountRepository: JpaRepository<UserAccountEntity, UUID> {

    @Query("""
        SELECT ua
        FROM UserAccountEntity ua
        WHERE ua.username = :username AND identifierType = :identifierType 
    """)
    fun findByUsername(username: String, identifierType: IdentifierType): UserAccountEntity?

}

fun UserAccountRepository.findByUsernameOrThrow(username: String, identifierType: IdentifierType): UserAccountEntity {
    return findByUsername(username, identifierType)
        ?: throw UserAccountNotFoundException(username)
}
