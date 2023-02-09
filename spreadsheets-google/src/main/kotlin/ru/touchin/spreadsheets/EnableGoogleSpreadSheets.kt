package ru.touchin.spreadsheets

import org.springframework.context.annotation.Import
import ru.touchin.spreadsheets.google.configurations.GoogleSpreadSheetsConfiguration

@Suppress("unused")
@Import(value = [GoogleSpreadSheetsConfiguration::class])
annotation class EnableGoogleSpreadSheets
