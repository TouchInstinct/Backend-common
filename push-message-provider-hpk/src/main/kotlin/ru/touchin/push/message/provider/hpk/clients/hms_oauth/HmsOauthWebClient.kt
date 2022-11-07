package ru.touchin.push.message.provider.hpk.clients.hms_oauth

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
import ru.touchin.push.message.provider.hpk.base.clients.dto.ConditionalResponse
import ru.touchin.push.message.provider.hpk.clients.hms_oauth.response.HmsOauthErrorResponse
import ru.touchin.push.message.provider.hpk.clients.hms_oauth.response.HmsOauthTokenResponse
import ru.touchin.push.message.provider.hpk.properties.HpkProperties

private const val GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials"
private const val METHOD_TOKEN = "token"

@Component
class HmsOauthWebClient(
    webClientLogger: WebClientLogger,
    webClientBuilder: WebClient.Builder,
    private val hpkProperties: HpkProperties,
    @Qualifier("push-message-provider.hpk.webclient-objectmapper")
    private val objectMapper: ObjectMapper,
) : ConfigurableWebClient(webClientLogger, webClientBuilder, hpkProperties.webServices.oauth) {

    override fun getObjectMapper(): ObjectMapper = objectMapper

    override fun getWebClient(): WebClient {
        return getWebClientBuilder(
            url = webService.url.toString(),
        )
            .setTimeouts()
            .build()
    }

    internal fun token(): ConditionalResponse<HmsOauthTokenResponse, HmsOauthErrorResponse> {
        return getWebClient()
            .post()
            .uri { builder ->
                builder
                    .path(METHOD_TOKEN)
                    .build()
            }
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(
                BodyInserters
                    .fromFormData("grant_type", GRANT_TYPE_CLIENT_CREDENTIALS)
                    .with("client_id", hpkProperties.webServices.clientId)
                    .with("client_secret", hpkProperties.webServices.oauth.clientSecret)
            )
            .exchangeWithWrap<HmsOauthTokenResponse, HmsOauthErrorResponse>(
                requestLogData = RequestLogData(
                    uri = METHOD_TOKEN,
                    logTags = listOf(),
                    method = HttpMethod.POST,
                ),
            )
            .block() ?: throw IllegalStateException("No response")
    }

}
