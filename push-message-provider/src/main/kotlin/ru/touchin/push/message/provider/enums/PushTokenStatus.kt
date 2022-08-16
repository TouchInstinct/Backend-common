package ru.touchin.push.message.provider.enums

enum class PushTokenStatus {

    /** Passes [PushMessageProviderType] validation. */
    VALID,

    /** Not passes [PushMessageProviderType] validation: not registered or has incorrect format. */
    INVALID,

    /** Could not validate. */
    UNKNOWN

}
