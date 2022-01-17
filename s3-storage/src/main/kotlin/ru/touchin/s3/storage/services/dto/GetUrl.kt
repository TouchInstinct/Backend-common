package ru.touchin.s3.storage.services.dto

import java.time.Duration

data class GetUrl(
    val id: String,
    val lifetime: Duration
)
