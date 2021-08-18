package ru.touchin.captcha.exceptions

import ru.touchin.common.exceptions.CommonException

class CaptchaScoreBelowMinimumException(
    score: Double,
) : CommonException("captcha score below minimum $score")
