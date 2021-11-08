package ru.touchin.s3.storage.services.dto

import java.io.File

data class UploadFile(
    val id: String,
    val file: File,
)
