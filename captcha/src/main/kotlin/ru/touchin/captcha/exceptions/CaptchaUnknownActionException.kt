package ru.touchin.captcha.exceptions

import ru.touchin.common.exceptions.CommonException

class CaptchaUnknownActionException(
    action: String
) : CommonException("unknown captcha action $action")
