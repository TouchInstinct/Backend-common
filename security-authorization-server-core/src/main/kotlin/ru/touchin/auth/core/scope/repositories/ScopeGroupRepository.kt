package ru.touchin.auth.core.scope.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.touchin.auth.core.scope.models.ScopeGroupEntity

interface ScopeGroupRepository : JpaRepository<ScopeGroupEntity, String>
