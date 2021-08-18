package ru.touchin.captcha.dto.enums

enum class CaptchaScore(val value: Double) {

    WEAK(0.2),
    AVERAGE(0.5),
    STRONG(0.8)

}
