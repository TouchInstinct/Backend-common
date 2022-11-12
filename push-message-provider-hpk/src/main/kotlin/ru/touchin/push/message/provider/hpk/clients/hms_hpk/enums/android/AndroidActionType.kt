package ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android

import ru.touchin.push.message.provider.hpk.base.enums.ValueableSerializableEnum

internal enum class AndroidActionType(
    override val value: Short
) : ValueableSerializableEnum<Short> {

    OPEN_APP_HOME_PAGE(0),
    OPEN_CUSTOM_APP_PAGE(1),
    OPEN_WEB_PAGE(2),
    DELETE_NOTIFICATION_MESSAGE(3),

    /** Only for Huawei devices */
    SHARE_NOTIFICATION_MESSAGE(4),

}
