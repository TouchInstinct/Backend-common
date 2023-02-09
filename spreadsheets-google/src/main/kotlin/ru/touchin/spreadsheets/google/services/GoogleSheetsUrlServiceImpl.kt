package ru.touchin.spreadsheets.google.services

import org.springframework.stereotype.Service
import ru.touchin.spreadsheets.google.exceptions.InvalidGoogleSpreadsheetsSourceException
import ru.touchin.spreadsheets.services.SpreadsheetsUrlService
import ru.touchin.spreadsheets.services.dto.SpreadsheetSource
import java.net.URL

@Service
class GoogleSheetsUrlServiceImpl : SpreadsheetsUrlService {

    override fun parse(url: URL): SpreadsheetSource {
        return parse(url.toString())
    }

    override fun parse(url: String): SpreadsheetSource {
        val groups = spreadSheetUrlRegex.find(url)?.groups
            ?: throw InvalidGoogleSpreadsheetsSourceException(url)

        val spreadSheetId = groups["spreadsheetId"]?.value
            ?: throw InvalidGoogleSpreadsheetsSourceException(url)

        val sheetId = groups["sheetId"]?.value?.toInt()
            ?: 0

        return SpreadsheetSource(spreadsheetId = spreadSheetId, sheetId = sheetId)
    }

    private companion object {

        val spreadSheetUrlRegex = Regex(
            "https://docs\\.google\\.com/spreadsheets/d/(?<spreadsheetId>[\\da-zA-Z-_]+)/edit(?:#gid=(?<sheetId>\\d+))?",
        )

    }

}
