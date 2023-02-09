package ru.touchin.spreadsheets.google.exceptions

import ru.touchin.common.exceptions.CommonException
import ru.touchin.spreadsheets.services.dto.SpreadsheetSource

class GoogleSheetMissingException private constructor(
    val source: SpreadsheetSource,
    message: String,
): CommonException(message) {

    companion object {
        fun create(source: SpreadsheetSource) = GoogleSheetMissingException(
            source = source,
            message = "Missing sheet '${source.sheetId}' in spreadsheet '${source.spreadsheetId}",
        )
    }

}
