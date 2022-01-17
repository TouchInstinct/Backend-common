package ru.touchin.s3.storage.services.dto

data class UploadBytes(
    val id: String,
    val bytes: ByteArray,
    val contentType: String?
)
