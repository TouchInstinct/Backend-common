package ru.touchin.captcha.exceptions

import ru.touchin.common.exceptions.CommonException

class CaptchaSiteVerifyFailureException(
    description: String?
) : CommonException(description)
