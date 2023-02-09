package ru.touchin.spreadsheets.services.dto

data class SpreadsheetSource(
    val spreadsheetId: String,
    val sheetId: Int,
    val headerRange: SheetRange = DEFAULT_HEADER_RANGE,
) {

    companion object {
        val DEFAULT_HEADER_RANGE = SheetRange(
            startColumnIndex = 0,
            startRowIndex = 0,
            endRowIndex = 1,
        )
    }

}
