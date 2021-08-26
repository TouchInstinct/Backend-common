package ru.touchin.common.messaging

import java.time.ZonedDateTime

interface BaseEvent {

    val firedAt: ZonedDateTime

}
