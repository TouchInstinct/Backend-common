package ru.touchin.s3.storage.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "s3")
data class S3Properties(
    val accessKeyId: String,
    val accessKey: String,
    val region: String,
    val bucket: String,
    val folder: String,
)

