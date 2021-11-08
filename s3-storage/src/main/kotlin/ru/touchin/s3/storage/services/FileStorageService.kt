package ru.touchin.s3.storage.services

import ru.touchin.s3.storage.services.dto.UploadFile

interface FileStorageService {

    fun upload(upload: UploadFile)

}
