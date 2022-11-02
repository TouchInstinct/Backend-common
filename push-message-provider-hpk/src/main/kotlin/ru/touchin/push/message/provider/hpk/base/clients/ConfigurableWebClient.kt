package ru.touchin.push.message.provider.hpk.base.clients

import io.netty.channel.ChannelOption
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.netty.http.client.HttpClient
import ru.touchin.common.spring.web.webclient.BaseLogWebClient
import ru.touchin.common.spring.web.webclient.dto.RequestLogData
import ru.touchin.common.spring.web.webclient.logger.WebClientLogger
import ru.touchin.push.message.provider.hpk.base.clients.dto.ConditionalResponse
import ru.touchin.push.message.provider.hpk.properties.HpkProperties
import java.util.concurrent.TimeUnit

abstract class ConfigurableWebClient(
    webClientLogger: WebClientLogger,
    webClientBuilder: WebClient.Builder,
    protected val webService: HpkProperties.WebService,
) : BaseLogWebClient(webClientLogger, webClientBuilder) {

    private val conditionalWebClientParser: Lazy<ConditionalWebClientParser> = lazy {
        ConditionalWebClientParser(
            objectMapper = getObjectMapper(),
        )
    }

    protected fun WebClient.Builder.setTimeouts(): WebClient.Builder {
        val httpClient: HttpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webService.http.connectionTimeout.toMillis().toInt())
            .doOnConnected { setup ->
                setup.addHandlerLast(ReadTimeoutHandler(webService.http.readTimeout.toMillis(), TimeUnit.MILLISECONDS))
                setup.addHandlerLast(WriteTimeoutHandler(webService.http.writeTimeout.toMillis(), TimeUnit.MILLISECONDS))
            }
            .let { httpClient ->
                webService.ssl?.let { ssl ->
                    httpClient.secure { builder ->
                        builder
                            .sslContext(SslContextBuilder.forClient().build())
                            .handshakeTimeout(ssl.handshakeTimeout)
                            .closeNotifyFlushTimeout(ssl.notifyFlushTimeout)
                            .closeNotifyReadTimeout(ssl.notifyReadTimeout)
                    }
                } ?: httpClient
            }

        return clientConnector(ReactorClientHttpConnector(httpClient))
    }

    internal inline fun <reified S, reified F> WebClient.RequestHeadersSpec<*>.exchangeWithWrap(
        requestLogData: RequestLogData,
    ): Mono<ConditionalResponse<S, F>> {
        return exchangeToMono { clientResponse ->
            parse<S, F>(clientResponse)
        }.doOnNext { responseWrapper ->
            getLogger().log(
                requestLogData.copy(
                    responseBody = responseWrapper.success ?: responseWrapper.failure
                )
            )
        }
    }

    internal inline fun <reified S, reified F> parse(
        clientResponse: ClientResponse,
    ): Mono<ConditionalResponse<S, F>> {
        val responseBody = clientResponse
            .bodyToMono(String::class.java)
            .defaultIfEmpty(String())
            .publishOn(Schedulers.parallel())

        return responseBody
            .map { body ->
                conditionalWebClientParser.value.parse(
                    clientResponse = clientResponse,
                    body = body,
                )
            }
    }

}
