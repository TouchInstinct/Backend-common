@file:Suppress("unused")

package ru.touchin.auth.core.tokens.access.exceptions

import ru.touchin.common.exceptions.CommonException

class AccessTokenMalformedException(description: String?) : CommonException(description)
