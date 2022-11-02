package ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android

import ru.touchin.push.message.provider.hpk.base.enums.ValueableSerializableEnum

enum class AndroidStyleType(
    override val value: Short
) : ValueableSerializableEnum<Short> {

    DEFAULT(0),
    BIG_TEXT(1),
    INBOX(3),

}
