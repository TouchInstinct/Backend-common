package ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android

import ru.touchin.push.message.provider.hpk.base.enums.ValueableSerializableEnum

internal enum class AndroidTargetUserType(
    override val value: Short
) : ValueableSerializableEnum<Short> {

    TEST_USER(1),
    FORMAL_USER(2),
    VOIP_USER(3),

}
