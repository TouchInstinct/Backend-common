package ru.touchin.spreadsheets.services

import ru.touchin.spreadsheets.services.dto.SheetCell
import ru.touchin.spreadsheets.services.dto.SheetHeader
import ru.touchin.spreadsheets.services.dto.SheetRange
import ru.touchin.spreadsheets.services.dto.Spreadsheet

interface SpreadsheetService {

    fun init(): Spreadsheet

    fun getSheet(): Spreadsheet

    fun getHeader(): SheetHeader

    fun createHeader(header: SheetHeader)

    fun getContentColumn(column: Int): List<Any>

    fun updateRows(range: SheetRange, values: List<List<SheetCell>>)

    fun updateContentRows(startRowIndex: Int, values: List<List<SheetCell>>)

}
