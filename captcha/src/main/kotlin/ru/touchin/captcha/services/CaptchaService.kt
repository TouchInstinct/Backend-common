package ru.touchin.captcha.services

import ru.touchin.captcha.dto.CaptchaVerificationResult

interface CaptchaService {

    fun verify(response: String): CaptchaVerificationResult

}
