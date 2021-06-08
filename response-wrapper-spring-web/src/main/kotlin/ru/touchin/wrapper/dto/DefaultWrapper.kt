package ru.touchin.wrapper.dto

@Suppress("unused")
class DefaultWrapper(
    override val result: Any?,
    val errorCode: Int,
    val errorMessage: String? = null
): Wrapper
