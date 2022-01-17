package ru.touchin.s3.storage.services.dto

data class UploadBytes(
    override val id: String,
    val bytes: ByteArray,
    override val contentType: String?
): UploadData
