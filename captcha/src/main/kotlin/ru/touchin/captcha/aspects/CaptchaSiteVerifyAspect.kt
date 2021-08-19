package ru.touchin.captcha.aspects

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import ru.touchin.captcha.annotations.Captcha
import ru.touchin.captcha.exceptions.CaptchaResponseMissingException
import ru.touchin.captcha.services.CaptchaService
import java.lang.IllegalStateException

@Aspect
@Component
@ConditionalOnProperty(prefix = "captcha", name = ["enabled"], havingValue = "true")
class CaptchaSiteVerifyAspect(private val captchaService: CaptchaService) {

    @Throws(Throwable::class)
    @Before("@annotation(captcha)")
    fun captchaSiteVerify(captcha: Captcha) {
        val currentRequestAttributes = RequestContextHolder.currentRequestAttributes()
            as? ServletRequestAttributes
            ?: throw IllegalStateException("unable to get current request attributes")

        val captchaResponse = currentRequestAttributes.request.getHeader(CAPTCHA_RESPONSE_HEADER_NAME)
            ?: throw CaptchaResponseMissingException()

        captchaService.verify(captchaResponse)
            .validateAction(captcha.action)
            .validateScore(captcha.minScore.value)
    }

    companion object {

        private const val CAPTCHA_RESPONSE_HEADER_NAME = "X-Captcha-Response"

    }

}
