package ru.touchin.common.exceptions

open class CommonException(
    description: String?,
    exception: Throwable? = null
) : RuntimeException(description.orEmpty(), exception)
