package ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android

import ru.touchin.push.message.provider.hpk.base.enums.ValueableSerializableEnum

internal enum class AndroidClickActionType(
    override val value: Short
) : ValueableSerializableEnum<Short> {

    CUSTOMIZE_ACTION(1),
    OPEN_URL(2),
    OPEN_APP(3),

}
