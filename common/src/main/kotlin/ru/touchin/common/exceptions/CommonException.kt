package ru.touchin.common.exceptions

open class CommonException(description: String?) : RuntimeException(description.orEmpty())
