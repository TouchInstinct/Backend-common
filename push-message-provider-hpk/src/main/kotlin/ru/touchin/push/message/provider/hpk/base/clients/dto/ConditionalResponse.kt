package ru.touchin.push.message.provider.hpk.base.clients.dto

internal open class ConditionalResponse<S, F>(
    val success: S?,
    val failure: F?,
) {

    init {
        // Only one value should be present
        val hasSuccessValue = success != null
        val hasFailureValue = failure != null

        check(hasSuccessValue != hasFailureValue)
    }

    val isSuccess: Boolean = success != null

}
