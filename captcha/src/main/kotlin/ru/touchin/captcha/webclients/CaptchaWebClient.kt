package ru.touchin.captcha.webclients

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import ru.touchin.captcha.properties.CaptchaProperties
import ru.touchin.captcha.dto.response.CaptchaSiteVerifyResponse
import ru.touchin.captcha.exceptions.CaptchaSiteVerifyFailureException
import ru.touchin.common.spring.web.webclient.BaseLogWebClient
import ru.touchin.common.spring.web.webclient.dto.RequestLogData
import ru.touchin.common.spring.web.webclient.logger.WebClientLogger

private const val SITE_VERIFY_PATH = "/recaptcha/api/siteverify"

@Component
class CaptchaWebClient(
    webClientLogger: WebClientLogger,
    webClientBuilder: WebClient.Builder,
    private val captchaProperties: CaptchaProperties,
) : BaseLogWebClient(webClientLogger, webClientBuilder) {

    override fun getWebClient(): WebClient {
        return getWebClientBuilder(captchaProperties.uri.toString()).build()
    }

    fun siteverify(response: String): CaptchaSiteVerifyResponse {
        return getWebClient().post()
            .uri {
                it.path(SITE_VERIFY_PATH)
                it.queryParam("response", response)
                it.queryParam("secret", captchaProperties.secret)

                it.build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .exchange(
                clazz = CaptchaSiteVerifyResponse::class.java,
                requestLogData = RequestLogData(
                    uri = SITE_VERIFY_PATH,
                    logTags = listOf("CAPTCHA_SITEVERIFY"),
                    method = HttpMethod.POST,
                )
            )
            .block()!!
            .also(this::validateSuccess)
    }

    private fun validateSuccess(captchaSiteVerifyResponse: CaptchaSiteVerifyResponse) {
        if (!captchaSiteVerifyResponse.success) {
            throw CaptchaSiteVerifyFailureException("Captcha siteverify request did not succeed")
        }
    }

}
