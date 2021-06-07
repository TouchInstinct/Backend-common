package ru.touchin.exception.handler.spring.logger

import ru.touchin.exception.handler.dto.ExceptionResolverResult

class FallbackLogger : Logger {

    override fun log(clazz: Class<*>, exceptionResolverResult: ExceptionResolverResult) {
        // do nothing
    }

}
