package ru.touchin.captcha.exceptions

import ru.touchin.common.exceptions.CommonException

class CaptchaActionMismatchException(
    action: String
) : CommonException("invalid captcha action $action")
