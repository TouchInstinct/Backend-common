package ru.touchin.auth.core.scope.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.findByIdOrNull
import ru.touchin.auth.core.scope.exceptions.ScopeNotFoundException
import ru.touchin.auth.core.scope.models.ScopeEntity

interface ScopeRepository : JpaRepository<ScopeEntity, String> {

    @Query("SELECT sg.scope FROM ScopeGroupEntity sg WHERE sg.groupName = :groupName")
    fun findByGroup(groupName: String): List<ScopeEntity>

}

fun ScopeRepository.findByIdOrThrow(scope: String): ScopeEntity {
    return findByIdOrNull(scope)
        ?: throw ScopeNotFoundException(scope)
}
