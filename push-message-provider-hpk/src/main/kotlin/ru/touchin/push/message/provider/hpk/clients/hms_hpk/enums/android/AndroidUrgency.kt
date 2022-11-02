package ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android

import ru.touchin.push.message.provider.hpk.base.enums.ValueableSerializableEnum

internal enum class AndroidUrgency(
    override val value: String
) : ValueableSerializableEnum<String> {

    HIGH("HIGH"),
    NORMAL("NORMAL"),

}
