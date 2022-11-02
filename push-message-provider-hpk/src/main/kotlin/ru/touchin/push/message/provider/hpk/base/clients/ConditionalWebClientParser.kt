package ru.touchin.push.message.provider.hpk.base.clients

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.reactive.function.client.ClientResponse
import ru.touchin.push.message.provider.hpk.base.clients.dto.ConditionalResponse

internal open class ConditionalWebClientParser(
    private val objectMapper: ObjectMapper,
) {

    open fun isOkResponse(clientResponse: ClientResponse): Boolean {
        return clientResponse.statusCode().is2xxSuccessful
    }

    @Throws(Exception::class)
    inline fun <reified S, reified F> parse(
        clientResponse: ClientResponse,
        body: String,
    ): ConditionalResponse<S, F> {
        return if (isOkResponse(clientResponse)) {
            ConditionalResponse<S, F>(
                success = parseValue(body, S::class.java),
                failure = null
            )
        } else {
            ConditionalResponse(
                success = null,
                failure = parseValue(body, F::class.java)
            )
        }
    }

    private fun <T> parseValue(source: String?, clazz: Class<T>): T {
        return if (clazz.canonicalName != String::class.java.canonicalName) {
            objectMapper.readValue(source, clazz)
        } else {
            @Suppress("UNCHECKED_CAST")
            source as T // T is String
        }
    }

}
