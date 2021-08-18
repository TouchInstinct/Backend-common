package ru.touchin.captcha.dto

import ru.touchin.captcha.exceptions.CaptchaActionMismatchException
import ru.touchin.captcha.exceptions.CaptchaScoreBelowMinimumException

data class CaptchaVerificationResult(val score: Double, val action: String) {

    fun validateScore(minScore: Double): CaptchaVerificationResult {
        if (score < minScore) {
            throw CaptchaScoreBelowMinimumException(score)
        }

        return this
    }

    fun validateAction(actionToValidate: String): CaptchaVerificationResult {
        if (action != actionToValidate) {
            throw CaptchaActionMismatchException(actionToValidate)
        }

        return this
    }

}
