package ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android

import ru.touchin.push.message.provider.hpk.base.enums.ValueableSerializableEnum

internal enum class AndroidImportance(
    override val value: String
) : ValueableSerializableEnum<String> {

    LOW("LOW"),
    NORMAL("NORMAL"),
    HIGH("HIGH"), // TODO: check if this type is still supported by HMS HPK API

}
