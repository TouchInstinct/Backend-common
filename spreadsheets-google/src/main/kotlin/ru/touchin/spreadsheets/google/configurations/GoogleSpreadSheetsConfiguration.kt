package ru.touchin.spreadsheets.google.configurations

import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import ru.touchin.spreadsheets.google.exceptions.GoogleCredentialsMissingException
import ru.touchin.spreadsheets.google.properties.GoogleSpreadsheetsProperties
import java.io.File
import java.io.InputStream

@Configuration
@ComponentScan("ru.touchin.spreadsheets.google")
@ConfigurationPropertiesScan("ru.touchin.spreadsheets.google")
class GoogleSpreadSheetsConfiguration {

    @Bean
    fun sheets(googleSheetsProperties: GoogleSpreadsheetsProperties): Sheets {
        val transport = NetHttpTransport.Builder().build()
        val jsonFactory = GsonFactory.getDefaultInstance()
        val initializer = HttpCredentialsAdapter(
            GoogleCredentials.fromStream(getCredentialsStream(googleSheetsProperties))
                .createScoped(SheetsScopes.SPREADSHEETS)
                .let { credentials ->
                    googleSheetsProperties.delegate
                        ?.let { user ->
                            credentials.createDelegated(user)
                        }
                        ?: credentials
                }
        )

        return Sheets.Builder(transport, jsonFactory, initializer)
            .setApplicationName(googleSheetsProperties.appName)
            .build()
    }

    private fun getCredentialsStream(googleSheetsProperties: GoogleSpreadsheetsProperties): InputStream {
        return googleSheetsProperties.credentialsPath
            ?.takeIf(String::isNotBlank)
            ?.let { File(it).inputStream() }
            ?: googleSheetsProperties.credentials?.byteInputStream()
            ?: throw GoogleCredentialsMissingException()
    }

}
