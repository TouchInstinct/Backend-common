@file:Suppress("unused")
package ru.touchin.exception.handler.spring.creators

import ru.touchin.common.spring.web.dto.ApiError
import ru.touchin.common.spring.web.dto.DefaultApiError

class DefaultExceptionResponseBodyCreatorImpl : ExceptionResponseBodyCreator {

    override fun invoke(apiError: ApiError): Any {
        return DefaultApiError(
            errorCode = apiError.errorCode,
            errorMessage = apiError.errorMessage
        )
    }

}
