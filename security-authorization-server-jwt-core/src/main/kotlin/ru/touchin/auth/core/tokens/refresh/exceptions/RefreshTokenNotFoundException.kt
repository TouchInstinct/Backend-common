package ru.touchin.auth.core.tokens.refresh.exceptions

import ru.touchin.common.exceptions.CommonNotFoundException

class RefreshTokenNotFoundException(value: String) : CommonNotFoundException(
    "No token found with value $value"
)
