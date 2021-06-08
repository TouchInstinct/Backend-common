package ru.touchin.exception.handler.spring.creators

import ru.touchin.common.spring.web.dto.ApiError

interface ExceptionResponseBodyCreator {

    operator fun invoke(apiError: ApiError): Any?

}
