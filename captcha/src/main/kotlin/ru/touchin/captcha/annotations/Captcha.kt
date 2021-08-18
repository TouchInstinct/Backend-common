package ru.touchin.captcha.annotations

import ru.touchin.captcha.dto.enums.CaptchaScore

@Target(AnnotationTarget.FUNCTION)
annotation class Captcha(
    val action: String,
    val minScore: CaptchaScore = CaptchaScore.AVERAGE
)
