package ru.touchin.settings.exceptions

import ru.touchin.common.exceptions.CommonNotFoundException

class SettingNotFoundException(key: String) : CommonNotFoundException(
    "Setting not found key=$key"
)
