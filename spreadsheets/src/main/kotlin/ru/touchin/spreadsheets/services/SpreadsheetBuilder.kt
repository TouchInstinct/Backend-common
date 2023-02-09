package ru.touchin.spreadsheets.services

import java.net.URL

interface SpreadsheetBuilder {

    fun setSheetId(sheetId: Int): SpreadsheetBuilder
    fun setAutoInit(autoInit: Boolean): SpreadsheetBuilder

    fun build(url: URL): SpreadsheetService

}
