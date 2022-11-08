package ru.touchin.push.message.provider.hpk.base.clients.dto

internal open class ConditionalResponse<S, F>(
    val success: S?,
    val failure: F?,
) {

    init {
        // Only one value should be present
        check((success == null) != (failure == null))
    }

    val isSuccess: Boolean = success != null

}
