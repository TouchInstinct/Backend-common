package ru.touchin.auth.core.user.exceptions

import ru.touchin.common.exceptions.CommonException

class WrongPasswordException(username: String) : CommonException(
    "Wrong password for user '$username'"
)
