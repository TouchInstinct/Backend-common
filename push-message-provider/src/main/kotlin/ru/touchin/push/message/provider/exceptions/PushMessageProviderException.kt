package ru.touchin.push.message.provider.exceptions

import ru.touchin.common.exceptions.CommonException

open class PushMessageProviderException(
    description: String,
    cause: Throwable?
) : CommonException(description, cause)
