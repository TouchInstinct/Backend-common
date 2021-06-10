package ru.touchin.settings.exceptions

import ru.touchin.common.exceptions.CommonException

class CannotParseSettingValueException(value: String, clazz: Class<*>, exception: Throwable) : CommonException(
    "Cannot parse setting value: $value to ${clazz.simpleName}",
    exception,
)
