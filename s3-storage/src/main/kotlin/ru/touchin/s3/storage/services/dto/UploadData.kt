package ru.touchin.s3.storage.services.dto

sealed interface UploadData {
    val id: String
    val contentType: String?
}
