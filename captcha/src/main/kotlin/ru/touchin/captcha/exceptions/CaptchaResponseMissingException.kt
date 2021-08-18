package ru.touchin.captcha.exceptions

import ru.touchin.common.exceptions.CommonException

class CaptchaResponseMissingException : CommonException("missing captcha response header")
