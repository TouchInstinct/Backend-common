package ru.touchin.s3.storage.services

import ru.touchin.s3.storage.services.dto.GetUrl
import ru.touchin.s3.storage.services.dto.UploadData
import java.net.URL

interface FileStorageService {

    fun upload(uploadFile: UploadData)
    fun getUrl(getUrl: GetUrl): URL?

}
