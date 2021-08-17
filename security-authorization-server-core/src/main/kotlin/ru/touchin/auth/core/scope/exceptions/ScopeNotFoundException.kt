package ru.touchin.auth.core.scope.exceptions

import ru.touchin.common.exceptions.CommonNotFoundException

class ScopeNotFoundException(scope: String) : CommonNotFoundException(
    "Scope not found name=$scope"
)
