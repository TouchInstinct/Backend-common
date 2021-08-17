package ru.touchin.auth.core.user.exceptions

import ru.touchin.common.exceptions.CommonNotFoundException

class UserAccountNotFoundException(username: String) : CommonNotFoundException(
    "User account not found username=$username"
)
