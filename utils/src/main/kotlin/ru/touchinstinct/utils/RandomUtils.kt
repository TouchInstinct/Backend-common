package ru.touchinstinct.utils

import java.util.UUID

fun getRandomUuidWithoutDashes() = UUID.randomUUID().toString().replace("-", "")

fun getRandomUuid() = UUID.randomUUID().toString()
