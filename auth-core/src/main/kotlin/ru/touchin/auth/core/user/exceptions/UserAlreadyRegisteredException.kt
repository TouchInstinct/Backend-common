package ru.touchin.auth.core.user.exceptions

import ru.touchin.common.exceptions.CommonException

class UserAlreadyRegisteredException(phoneNumber: String) : CommonException(
    "User '$phoneNumber' already registered"
)
