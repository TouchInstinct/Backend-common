package ru.touchin.exception.handler.spring.advices

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.touchin.exception.handler.dto.ExceptionResolverResult
import ru.touchin.exception.handler.spring.creators.ExceptionResponseBodyCreator
import ru.touchin.exception.handler.spring.logger.Logger
import ru.touchin.exception.handler.spring.resolvers.ExceptionResolver

@RestControllerAdvice
class ExceptionHandlerAdvice(
    exceptionResolversList: List<ExceptionResolver>,
    private val logger: Logger,
    private val exceptionResponseBodyCreator: ExceptionResponseBodyCreator,
) {

    private val exceptionResolvers = exceptionResolversList.asSequence()

    @ExceptionHandler(Exception::class)
    fun handleException(
        exception: Exception,
    ): ResponseEntity<Any> {
        val result: ExceptionResolverResult = exceptionResolvers
            .mapNotNull { it.invoke(exception) }
            .firstOrNull()
            ?: ExceptionResolverResult.createInternalError("Unexpected exception occurred: $exception")

        logger.log(this::class.java, result)

        val body = exceptionResponseBodyCreator(result.apiError)

        val headers = HttpHeaders().apply {
            set("X-Error-Code", result.apiError.errorCode.toString())
            set("X-Error-Message", result.apiError.errorMessage)
        }

        return ResponseEntity(body, headers, result.status)
    }

}
