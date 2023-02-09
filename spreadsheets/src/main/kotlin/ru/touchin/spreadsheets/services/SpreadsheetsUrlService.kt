package ru.touchin.spreadsheets.services

import ru.touchin.spreadsheets.services.dto.SpreadsheetSource
import java.net.URL

interface SpreadsheetsUrlService {

    fun parse(url: URL): SpreadsheetSource

    fun parse(url: String): SpreadsheetSource

}
