package ru.touchin.s3.storage.services

import ru.touchin.s3.storage.services.dto.DeleteData
import ru.touchin.s3.storage.services.dto.GetUrl
import ru.touchin.s3.storage.services.dto.UploadData
import java.net.URL

interface FileStorageService {

    fun upload(uploadData: UploadData)
    fun delete(deleteData: DeleteData)
    fun getUrl(getUrl: GetUrl): URL

}
