package ru.touchin.s3.storage.services

import org.springframework.stereotype.Service
import ru.touchin.s3.storage.properties.S3Properties
import ru.touchin.s3.storage.services.dto.UploadFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest

@Service
class S3FileStorageServiceImpl(
    private val s3Properties: S3Properties,
    private val s3Client: S3Client,
) : FileStorageService {

    private val folder = normalizeDirectoryPath(s3Properties.folder)

    override fun upload(upload: UploadFile) {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(s3Properties.bucket)
            .key(keyOf(upload.id))
            .build()

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(upload.file))
    }

    fun keyOf(fileId: String) = folder + fileId

    private fun normalizeDirectoryPath(folder: String): String {
        return when {
            folder.isBlank() -> ""
            folder.endsWith("/") -> folder
            else -> "$folder/"
        }
    }

}
