@file:Suppress("unused")
package ru.touchin.logger.dto

data class LogError(
    val message: String?,
    val trace: String?
) {

    companion object {
        const val ERROR_TAG = "error"
        const val ERROR_FATAL_TAG = "errorFatal"
        const val ERROR_BASE_TAG = "errorBase"

        fun Throwable.stackTraceAsString() = this.stackTrace?.fold("") { a, b ->
            "$a$b\n"
        }

        fun Throwable.toLogError() = LogError(
            message = this.message,
            trace = this.stackTraceAsString()
        )
    }

}
