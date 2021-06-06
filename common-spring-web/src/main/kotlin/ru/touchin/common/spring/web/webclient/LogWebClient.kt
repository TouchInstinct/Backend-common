@file:Suppress("unused")
package ru.touchin.common.spring.web.webclient

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import ru.touchin.common.spring.web.webclient.dto.RequestLogData
import ru.touchin.common.spring.web.webclient.logger.WebClientLogger

interface LogWebClient {

    fun getLogger(): WebClientLogger

    fun getObjectMapper(): ObjectMapper

    fun <T> WebClient.RequestHeadersSpec<*>.exchange(
        clazz: Class<T>,
        requestLogData: RequestLogData,
    ): Mono<T> {
        return exchangeToMono { clientResponse ->
            clientResponse.bodyToMono(String::class.java)
        }.map { responseBody ->
            getLogger().log(requestLogData.copy(responseBody = responseBody))

            parseValue(responseBody, clazz)
        }

    }

    private fun <T> parseValue(source: String?, clazz: Class<T>): T {
        return if (clazz.canonicalName != String::class.java.canonicalName) {
            getObjectMapper().readValue(source, clazz)
        } else {
            @Suppress("UNCHECKED_CAST")
            source as T // T is String
        }
    }

}


