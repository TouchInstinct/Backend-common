package ru.touchin.spreadsheets.google.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spreadsheets.google")
data class GoogleSpreadsheetsProperties(
    val appName: String,
    val credentials: String? = null,
    val credentialsPath: String? = null,
    val delegate: String? = null,
)
