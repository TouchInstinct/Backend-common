package ru.touchin.push.message.provider.hpk.clients.hms_hpk

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import ru.touchin.common.spring.web.webclient.dto.RequestLogData
import ru.touchin.common.spring.web.webclient.logger.WebClientLogger
import ru.touchin.push.message.provider.hpk.base.clients.ConfigurableWebClient
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.bodies.HmsHpkMessagesSendBody
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.requests.HmsHpkMessagesSendRequest
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.responses.HmsHpkResponse
import ru.touchin.push.message.provider.hpk.properties.HpkProperties

/**
 * Client for Huawei Push Kit.
 * @see <a href="https://developer.huawei.com/consumer/en/doc/development/HMSCore-References/https-send-api-0000001050986197">Documentation</a>
 */
@Component
class HmsHpkWebClient(
    webClientLogger: WebClientLogger,
    @Qualifier("push-message-provider.hpk.hms-hpk-webclient-builder")
    webClientBuilder: WebClient.Builder,
    private val hpkProperties: HpkProperties,
    @Qualifier("push-message-provider.hpk.webclient-objectmapper")
    private val objectMapper: ObjectMapper,
) : ConfigurableWebClient(webClientLogger, webClientBuilder, hpkProperties.webServices.hpk) {

    override fun getObjectMapper(): ObjectMapper = objectMapper

    override fun getWebClient(): WebClient {
        return getWebClientBuilder(
            url = webService.url.toString(),
        )
            .setTimeouts()
            .build()
    }

    internal fun messagesSend(hmsHpkMessagesSendRequest: HmsHpkMessagesSendRequest): HmsHpkResponse {
        val url = "${hpkProperties.webServices.clientId}/$METHOD_MESSAGES_SEND"

        return getWebClient().post()
            .uri { builder ->
                builder
                    .path(url)
                    .build()
            }
            .contentType(MediaType.APPLICATION_JSON)
            .headers { it.setBearerAuth(hmsHpkMessagesSendRequest.accessToken) }
            .body(BodyInserters.fromValue(hmsHpkMessagesSendRequest.hmsHpkMessagesSendBody))
            .exchange(
                clazz = HmsHpkResponse::class.java,
                requestLogData = RequestLogData(
                    uri = url,
                    logTags = listOf(),
                    method = HttpMethod.POST,
                )
            )
            .block() ?: throw IllegalStateException("No response")
    }

    private companion object {

        const val METHOD_MESSAGES_SEND = "messages:send"

    }

}
