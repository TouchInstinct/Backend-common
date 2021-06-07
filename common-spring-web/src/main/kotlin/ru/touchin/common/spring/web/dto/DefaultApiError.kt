package ru.touchin.common.spring.web.dto

class DefaultApiError(
    override val errorCode: Int,
    override val errorMessage: String? = null
): ApiError {

    companion object {
        fun createFailure(errorMessage: String? = null) = DefaultApiError(
            errorCode = ApiError.FAILURE_CODE,
            errorMessage = errorMessage
        )
    }

}
