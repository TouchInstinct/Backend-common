package ru.touchin.auth.core.user.exceptions

import ru.touchin.common.exceptions.CommonNotFoundException
import java.util.*

class UserNotFoundException(userId: UUID) : CommonNotFoundException (
    "User not found id=$userId"
)
