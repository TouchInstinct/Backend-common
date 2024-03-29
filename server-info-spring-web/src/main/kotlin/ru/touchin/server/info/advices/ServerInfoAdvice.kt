package ru.touchin.server.info.advices

import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import ru.touchin.server.info.services.ServerInfoHeader

@RestControllerAdvice
class ServerInfoAdvice(
    private val serverInfoHeaders: List<ServerInfoHeader>
) : ResponseBodyAdvice<Any> {

    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>>
    ): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        for (service in serverInfoHeaders) {
            val serverInfo = service.getHeaders()

            serverInfo.map {
                response
                    .headers
                    .add(it.key, it.value)
            }
        }

        return body
    }

}
