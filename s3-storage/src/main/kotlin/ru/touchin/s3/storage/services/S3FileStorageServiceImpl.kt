package ru.touchin.s3.storage.services

import org.springframework.stereotype.Service
import ru.touchin.s3.storage.properties.S3Properties
import ru.touchin.s3.storage.services.dto.GetUrl
import ru.touchin.s3.storage.services.dto.UploadBytes
import ru.touchin.s3.storage.services.dto.UploadFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest
import java.net.URL

@Service
class S3FileStorageServiceImpl(
    private val s3Properties: S3Properties,
    private val s3Client: S3Client,
    private val s3Presigner: S3Presigner
) : FileStorageService {

    private val folder = normalizeDirectoryPath(s3Properties.folder)

    override fun upload(uploadFile: UploadFile) {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(s3Properties.bucket)
            .key(keyOf(uploadFile.id))
            .contentType(uploadFile.contentType ?: DEFAULT_CONTENT_TYPE)
            .build()

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(uploadFile.file))
    }

    override fun upload(uploadBytes: UploadBytes) {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(s3Properties.bucket)
            .key(keyOf(uploadBytes.id))
            .contentType(uploadBytes.contentType ?: DEFAULT_CONTENT_TYPE)
            .build()

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(uploadBytes.bytes))
    }

    override fun getUrl(getUrl: GetUrl): URL? {
        val getObjectRequest = GetObjectRequest.builder()
            .bucket(s3Properties.bucket)
            .key(keyOf(getUrl.id))
            .build()

        val getObjectPresignRequest = GetObjectPresignRequest.builder()
            .getObjectRequest(getObjectRequest)
            .signatureDuration(getUrl.lifetime)
            .build()

        val presignedGetObjectRequest = s3Presigner
            .presignGetObject(getObjectPresignRequest)
            .takeIf(PresignedGetObjectRequest::isBrowserExecutable)

        return presignedGetObjectRequest?.url()
    }

    fun keyOf(fileId: String) = folder + fileId

    private fun normalizeDirectoryPath(folder: String): String {
        return when {
            folder.isBlank() -> ""
            folder.endsWith("/") -> folder
            else -> "$folder/"
        }
    }

    companion object {
        private const val DEFAULT_CONTENT_TYPE = "application/octet-stream"
    }

}
