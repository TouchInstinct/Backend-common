package ru.touchin.push.message.provider.hpk.base.extensions

import ru.touchin.push.message.provider.hpk.base.builders.Buildable

fun <V, B : Buildable> B.ifNotNull(value: V?, setter: B.(V) -> B): B {
    return value?.let { setter(it) } ?: this
}
