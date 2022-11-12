package ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android

import ru.touchin.push.message.provider.hpk.base.enums.ValueableSerializableEnum

internal enum class AndroidFastAppTargetType(
    override val value: Short
) : ValueableSerializableEnum<Short> {

    DEVELOPMENT(1),
    PRODUCTION(2),

}
