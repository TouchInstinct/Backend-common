package ru.touchin.auth.core.tokens.refresh.exceptions

import ru.touchin.common.exceptions.CommonException

class RefreshTokenExpiredException(value: String) : CommonException(
    "Refresh token with value $value is expired"
)
