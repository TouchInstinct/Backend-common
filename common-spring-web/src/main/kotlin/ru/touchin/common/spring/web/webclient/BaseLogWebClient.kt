@file:Suppress("unused")
package ru.touchin.common.spring.web.webclient

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.http.HttpStatus
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import ru.touchin.common.spring.web.webclient.errors.WebClientStatusException
import ru.touchin.common.spring.web.webclient.logger.WebClientLogger

abstract class BaseLogWebClient(
   private val webClientLogger: WebClientLogger,
   private val builder: WebClient.Builder,
) : LogWebClient {

    protected open var strategies: ExchangeStrategies? = null

    protected open var clientConnector: ClientHttpConnector? = null

    override fun getLogger(): WebClientLogger {
        return webClientLogger
    }

    override fun getObjectMapper(): ObjectMapper {
        return defaultObjectMapper
    }

    protected inline fun <reified T : Any> checkServiceAvailable(clientResponse: ClientResponse): Mono<T> {
        return when (val status = clientResponse.statusCode()) {
            HttpStatus.OK -> clientResponse.bodyToMono()
            else -> throw WebClientStatusException(
                "Status code $status",
                status,
            )
        }
    }

    protected fun getWebClientBuilder(url: String): WebClient.Builder {
        val webClient = builder.baseUrl(url)

        this.clientConnector?.let {
            webClient.clientConnector(it)
        }

        this.strategies?.let {
            webClient.exchangeStrategies(it)
        }

        return webClient
    }

    abstract fun getWebClient(): WebClient

    companion object {
        val defaultObjectMapper = ObjectMapper()
            .registerModule(KotlinModule())
            .registerModule(JavaTimeModule())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            ?: throw IllegalStateException("unable to retrieve ObjectMapper")
    }

}
