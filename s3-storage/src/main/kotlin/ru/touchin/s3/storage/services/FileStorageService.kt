package ru.touchin.s3.storage.services

import ru.touchin.s3.storage.services.dto.GetUrl
import ru.touchin.s3.storage.services.dto.UploadBytes
import ru.touchin.s3.storage.services.dto.UploadFile
import java.net.URL

interface FileStorageService {

    fun upload(uploadFile: UploadFile)
    fun upload(uploadBytes: UploadBytes)
    fun getUrl(getUrl: GetUrl): URL?

}
