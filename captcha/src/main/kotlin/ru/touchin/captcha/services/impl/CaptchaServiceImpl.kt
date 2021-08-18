package ru.touchin.captcha.services.impl

import org.springframework.stereotype.Service
import ru.touchin.captcha.dto.CaptchaVerificationResult
import ru.touchin.captcha.exceptions.CaptchaUnknownActionException
import ru.touchin.captcha.properties.CaptchaProperties
import ru.touchin.captcha.services.CaptchaService
import ru.touchin.captcha.webclients.CaptchaWebClient
import ru.touchin.logger.spring.annotations.AutoLogging
import ru.touchin.logger.spring.annotations.LogValue

@Service
class CaptchaServiceImpl(
    private val captchaWebClient: CaptchaWebClient,
    private val captchaProperties: CaptchaProperties,
) : CaptchaService {

    @LogValue
    @AutoLogging(tags = ["CAPTCHA", "CAPTCHA_VERIFICATION"])
    override fun verify(response: String): CaptchaVerificationResult {
        val siteVerifyResponse = captchaWebClient.siteVerify(response)

        if (siteVerifyResponse.action !in captchaProperties.actions) {
            throw CaptchaUnknownActionException(siteVerifyResponse.action)
        }

        return CaptchaVerificationResult(
            score = siteVerifyResponse.score,
            action = siteVerifyResponse.action,
        )
    }

}
