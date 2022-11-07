package ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android

import ru.touchin.push.message.provider.hpk.base.enums.ValueableSerializableEnum

internal enum class AndroidVisibility(
    override val value: String
) : ValueableSerializableEnum<String> {

    /** The visibility is not specified. This value is equivalent to PRIVATE. */
    VISIBILITY_UNSPECIFIED("VISIBILITY_UNSPECIFIED"),

    /**
     * If you have set a lock screen password and enabled Hide notification content under Settings > Notifications,
     * the content of a received notification message is hidden on the lock screen.
     * */
    PRIVATE("PRIVATE"),

    /** The content of a received notification message is displayed on the lock screen. */
    PUBLIC("PUBLIC"),

    /** A received notification message is not displayed on the lock screen. */
    SECRET("SECRET"),

}
