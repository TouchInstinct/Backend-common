package ru.touchin.s3.storage.services.dto

import java.io.File

data class UploadFile(
    override val id: String,
    val file: File,
    override val contentType: String?
): UploadData
