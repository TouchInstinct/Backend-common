package ru.touchin.s3.storage.configurations

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import ru.touchin.s3.storage.properties.S3Properties
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import java.net.URI

@ComponentScan("ru.touchin.s3.storage")
@ConfigurationPropertiesScan("ru.touchin.s3.storage")
class S3Configuration(private val s3Properties: S3Properties) {

    private val region = Region.of(s3Properties.region)

    @Bean
    fun s3Client(): S3Client {
        return S3Client.builder()
            .region(region)
            .apply { if (s3Properties.endpoint != null) endpointOverride(URI.create(s3Properties.endpoint)) }
            .credentialsProvider { getCredentialsProvider() }
            .build()
    }

    @Bean
    fun s3Presigner(s3Properties: S3Properties): S3Presigner {
        return S3Presigner.builder()
            .region(region)
            .apply { if (s3Properties.endpoint != null) endpointOverride(URI.create(s3Properties.endpoint)) }
            .credentialsProvider { getCredentialsProvider() }
            .build()
    }

    private fun getCredentialsProvider(): AwsBasicCredentials {
        return AwsBasicCredentials.create(s3Properties.accessKeyId, s3Properties.accessKey)
    }

}
