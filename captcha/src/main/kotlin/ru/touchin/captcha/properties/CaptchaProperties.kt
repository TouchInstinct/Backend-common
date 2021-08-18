package ru.touchin.captcha.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.net.URI

@ConstructorBinding
@ConfigurationProperties(prefix = "captcha")
data class CaptchaProperties(
    val uri: URI,
    val secret: String,
    val actions: List<String>
)
