package ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.apns

import ru.touchin.push.message.provider.hpk.base.enums.ValueableSerializableEnum

enum class ApnsPriority(
    override val value: Short
) : ValueableSerializableEnum<Short> {

    SEND_BY_GROUP(5),
    SEND_IMMIDIATELY(10),

}
