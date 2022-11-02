package ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android

import ru.touchin.push.message.provider.hpk.base.enums.ValueableSerializableEnum

enum class AndroidIntentType(
    override val value: Short
) : ValueableSerializableEnum<Short> {

    INTENT(0),
    ACTION(1),

}
