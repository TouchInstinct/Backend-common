package ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.web

import ru.touchin.push.message.provider.hpk.base.enums.ValueableSerializableEnum

enum class WebUrgency(
    override val value: String
) : ValueableSerializableEnum<String> {

    VERY_LOW("very-low"),
    LOW("low"),
    NORMAL("normal"),
    HIGH("high"),

}
