package ru.touchin.spreadsheets.google.services

import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.BatchGetValuesByDataFilterRequest
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest
import com.google.api.services.sheets.v4.model.CellData
import com.google.api.services.sheets.v4.model.DataFilter
import com.google.api.services.sheets.v4.model.DimensionRange
import com.google.api.services.sheets.v4.model.ExtendedValue
import com.google.api.services.sheets.v4.model.GridCoordinate
import com.google.api.services.sheets.v4.model.GridRange
import com.google.api.services.sheets.v4.model.InsertDimensionRequest
import com.google.api.services.sheets.v4.model.Request
import com.google.api.services.sheets.v4.model.RowData
import com.google.api.services.sheets.v4.model.UpdateCellsRequest
import ru.touchin.spreadsheets.google.enums.Dimension
import ru.touchin.spreadsheets.google.enums.ValueRenderOption
import ru.touchin.spreadsheets.google.exceptions.GoogleSheetMissingException
import ru.touchin.spreadsheets.services.SpreadsheetService
import ru.touchin.spreadsheets.services.dto.CellType
import ru.touchin.spreadsheets.services.dto.SheetCell
import ru.touchin.spreadsheets.services.dto.SheetHeader
import ru.touchin.spreadsheets.services.dto.SheetRange
import ru.touchin.spreadsheets.services.dto.Spreadsheet
import ru.touchin.spreadsheets.services.dto.SpreadsheetSource
import ru.touchin.spreadsheets.services.dto.cells.SheetStringCell
import java.math.BigDecimal
import kotlin.math.max

internal class GoogleSpreadsheetServiceImpl(
    private val source: SpreadsheetSource,
    private val sheets: Sheets,
) : SpreadsheetService {

    private lateinit var spreadsheet: Spreadsheet

    override fun init(): Spreadsheet {
        val spreadsheet = sheets.spreadsheets()
            .get(source.spreadsheetId)
            .execute()

        val sheet = spreadsheet.sheets
            .find { it.properties.sheetId == source.sheetId }
            ?: throw GoogleSheetMissingException.create(source)

        val gridProperties = sheet.properties.gridProperties

        return Spreadsheet(
            rowCount = gridProperties.rowCount,
            columnCount = gridProperties.columnCount,
            locale = spreadsheet.properties.locale,
        ).also {
            this.spreadsheet = it
        }
    }

    override fun getSheet(): Spreadsheet {
        return spreadsheet.copy()
    }

    override fun getHeader(): SheetHeader {
        val values = getValues(source.headerRange)
            .map { rows ->
                rows.map { SheetStringCell(it.toString()) }
            }

        return SheetHeader(values)
    }

    override fun createHeader(header: SheetHeader) {
        updateValues(source.headerRange, header.values)
    }

    override fun getContentColumn(column: Int): List<Any> {
        return getValues(
            getContentRange().copy(
                startColumnIndex = column,
                endColumnIndex = column + 1,
            ),
            dimension = Dimension.Columns,
        )
            .firstOrNull()
            ?: emptyList()
    }

    override fun updateRows(range: SheetRange, values: List<List<SheetCell>>) {
        return updateValues(range, values)
    }

    override fun updateContentRows(startRowIndex: Int, values: List<List<SheetCell>>) {
        updateRows(
            range = SheetRange(
                startColumnIndex = 0,
                startRowIndex = startRowIndex + (source.headerRange.endRowIndex ?: 0),
            ),
            values = values,
        )
    }

    private fun updateValues(range: SheetRange, values: List<List<SheetCell>>) {
        val maxColumnIndex = getMaxIndex(values, Dimension.Columns) + range.startColumnIndex
        val maxRowIndex = getMaxIndex(values, Dimension.Rows) + range.startRowIndex

        val requests = mutableListOf<Request>()

        if (maxColumnIndex > spreadsheet.columnCount) {
            requests.add(
                createInsertRequest(
                    insertCount = maxColumnIndex - spreadsheet.columnCount,
                    dimension = Dimension.Columns,
                )
            )
        }

        if (maxRowIndex > spreadsheet.rowCount) {
            requests.add(
                createInsertRequest(
                    insertCount = maxRowIndex - spreadsheet.rowCount,
                    dimension = Dimension.Rows,
                )
            )
        }

        requests.add(
            createUpdateCellsRequest(range, values)
        )

        sheets.spreadsheets()
            .batchUpdate(
                source.spreadsheetId,
                BatchUpdateSpreadsheetRequest().apply {
                    this.requests = requests
                }
            )
            .execute()

        spreadsheet = spreadsheet.copy(
            columnCount = max(spreadsheet.columnCount, maxColumnIndex),
            rowCount = max(spreadsheet.rowCount, maxRowIndex),
        )
    }

    private fun createUpdateCellsRequest(range: SheetRange, values: List<List<SheetCell>>): Request {
        return Request().setUpdateCells(
            UpdateCellsRequest().apply {
                fields = "*"
                start = GridCoordinate().apply {
                    sheetId = source.sheetId
                    columnIndex = range.startColumnIndex
                    rowIndex = range.startRowIndex
                }
                rows = values.map { rows ->
                    RowData().setValues(
                        rows.map { row ->
                            row.toCellData()
                        }
                    )
                }
            }
        )
    }

    private fun getContentRange(): SheetRange {
        return SheetRange(
            startColumnIndex = 0,
            startRowIndex = source.headerRange.endRowIndex ?: 0,
        )
    }

    private fun SheetCell.toCellData(): CellData {
        val value = ExtendedValue()

        when(type) {
            CellType.String -> value.stringValue = this.value.toString()
            CellType.Null -> value.stringValue = null
            CellType.Date -> value.stringValue = this.value.toString()
            CellType.DateTime -> value.stringValue = this.value.toString()
            CellType.Price -> value.numberValue = (this.value as BigDecimal).toDouble()
            CellType.Number -> value.numberValue = (this.value as Number).toDouble()
        }

        return CellData().also { it.userEnteredValue = value }
    }

    private fun createInsertRequest(insertCount: Int, dimension: Dimension): Request {
        val startIndex = when(dimension) {
            Dimension.Rows -> spreadsheet.rowCount - 1
            Dimension.Columns -> spreadsheet.columnCount - 1
        }

        return Request().setInsertDimension(
            InsertDimensionRequest()
                .setRange(
                    DimensionRange()
                        .setSheetId(source.sheetId)
                        .setDimension(dimension.value)
                        .setStartIndex(startIndex)
                        .setEndIndex(startIndex + insertCount)
                )
        )
    }

    private fun getMaxIndex(values: List<List<SheetCell>>, dimension: Dimension): Int {
        return when (dimension) {
            Dimension.Rows -> values.size
            Dimension.Columns -> values.maxOf { it.size }
        }
    }

    private fun getValues(range: SheetRange, dimension: Dimension = Dimension.Rows): List<List<Any>> {
        val values = sheets.spreadsheets()
            .values()
            .batchGetByDataFilter(
                source.spreadsheetId,
                BatchGetValuesByDataFilterRequest().apply {
                    majorDimension = dimension.value
                    valueRenderOption = ValueRenderOption.FORMATTED_VALUE.code
                    dataFilters = listOf(
                        DataFilter().apply {
                            gridRange = range.toGridRange()
                        }
                    )
                }
            )
            .execute()
            .valueRanges
            .first()
            .valueRange
            .getValues()

        return values?.toList() ?: emptyList()
    }

    private fun SheetRange.toGridRange(): GridRange {
        return GridRange().also {
            it.sheetId = source.sheetId
            it.startColumnIndex = startColumnIndex
            it.startRowIndex = startRowIndex
            it.endColumnIndex = endColumnIndex
            it.endRowIndex = endRowIndex
        }
    }

}
