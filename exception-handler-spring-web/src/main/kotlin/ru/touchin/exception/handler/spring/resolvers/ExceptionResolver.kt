package ru.touchin.exception.handler.spring.resolvers

import ru.touchin.exception.handler.dto.ExceptionResolverResult

interface ExceptionResolver {

    operator fun invoke(exception: Exception): ExceptionResolverResult?

}
