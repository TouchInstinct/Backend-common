package ru.touchin.s3.storage.annotations

import org.springframework.context.annotation.Import
import ru.touchin.s3.storage.configurations.S3Configuration

/**
 * Annotation to add Amazon S3 Storage components.
 */
@Import(S3Configuration::class)
annotation class EnableS3Storage
