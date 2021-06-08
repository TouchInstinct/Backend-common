@file:Suppress("unused")
package ru.touchin.wrapper.advices

import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import ru.touchin.wrapper.annotations.NoResponseWrap
import ru.touchin.wrapper.annotations.ResponseWrap
import ru.touchin.wrapper.components.ResponseBodyWrapper

@RestControllerAdvice(annotations = [ResponseWrap::class])
class WrapResponseAdvice(
    private val responseWrapper: ResponseBodyWrapper
): ResponseBodyAdvice<Any> {

    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return !returnType.hasMethodAnnotation(NoResponseWrap::class.java)
    }

    /***
     * Не будет работать, если контроллер возвращает тип String, так как по умолчанию будет выбираться конвертер для строки.
     * Решить проблему можно так: https://stackoverflow.com/questions/44121648/controlleradvice-responsebodyadvice-failed-to-enclose-a-string-response
     */
    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any {
        return responseWrapper.wrap(body)
    }

}
