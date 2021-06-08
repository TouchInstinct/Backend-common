package ru.touchin.exception.handler.spring.logger

import ru.touchin.exception.handler.dto.ExceptionResolverResult

interface Logger {

    fun log(clazz: Class<*>, exceptionResolverResult: ExceptionResolverResult)

}
