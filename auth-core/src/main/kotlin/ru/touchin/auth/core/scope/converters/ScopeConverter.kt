package ru.touchin.auth.core.scope.converters

import ru.touchin.auth.core.scope.dto.Scope
import ru.touchin.auth.core.scope.models.ScopeEntity

object ScopeConverter {

    fun ScopeEntity.toDto(): Scope {
        return Scope(name)
    }

}
