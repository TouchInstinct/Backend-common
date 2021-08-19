package ru.touchin.exception.handler.dto

import org.springframework.http.HttpStatus
import ru.touchin.common.spring.web.dto.ApiError
import ru.touchin.common.spring.web.dto.DefaultApiError

data class ExceptionResolverResult(
    val apiError: ApiError,
    val status: HttpStatus,
    val exception: Exception?,
) {

    companion object {

        fun createInternalError(errorMessage: String?): ExceptionResolverResult {
            return ExceptionResolverResult(
                apiError = DefaultApiError.createFailure(errorMessage),
                status = HttpStatus.INTERNAL_SERVER_ERROR,
                exception = null
            )
        }

        fun createInternalError(exception: Exception?): ExceptionResolverResult {
            return ExceptionResolverResult(
                apiError = DefaultApiError.createFailure(exception?.message),
                status = HttpStatus.INTERNAL_SERVER_ERROR,
                exception = exception
            )
        }

        fun createNotFoundError(exception: Exception?): ExceptionResolverResult {
            return ExceptionResolverResult(
                apiError = DefaultApiError(
                    errorCode = ApiError.NOT_FOUND_CODE,
                    errorMessage = exception?.message
                ),
                status = HttpStatus.NOT_FOUND,
                exception = exception
            )
        }

    }

}
