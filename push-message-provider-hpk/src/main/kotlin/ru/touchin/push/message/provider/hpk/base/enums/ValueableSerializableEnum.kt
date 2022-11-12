package ru.touchin.push.message.provider.hpk.base.enums

import com.fasterxml.jackson.annotation.JsonValue

internal interface ValueableSerializableEnum<T> {

    val value: T

    @JsonValue
    fun toValue(): T = value

}
