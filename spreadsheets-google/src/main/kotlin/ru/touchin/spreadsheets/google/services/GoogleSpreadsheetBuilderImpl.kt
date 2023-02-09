package ru.touchin.spreadsheets.google.services

import com.google.api.services.sheets.v4.Sheets
import org.springframework.stereotype.Component
import ru.touchin.spreadsheets.services.SpreadsheetBuilder
import ru.touchin.spreadsheets.services.SpreadsheetService
import ru.touchin.spreadsheets.services.SpreadsheetsUrlService
import java.net.URL

@Component
class GoogleSpreadsheetBuilderImpl(
    private val spreadsheetsUrlService: SpreadsheetsUrlService,
    private val sheets: Sheets,
) : SpreadsheetBuilder {

    private var sheetId: Int? = null

    private var autoInit: Boolean = true

    override fun setSheetId(sheetId: Int): SpreadsheetBuilder = this.also {
        it.sheetId = sheetId
    }

    override fun setAutoInit(autoInit: Boolean): SpreadsheetBuilder = this.also {
        it.autoInit = autoInit
    }

    override fun build(url: URL): SpreadsheetService {
        val source = spreadsheetsUrlService.parse(url)
        val sheetId = this.sheetId ?: source.sheetId

        return GoogleSpreadsheetServiceImpl(source.copy(sheetId = sheetId), sheets)
            .also {
                if (autoInit) {
                    it.init()
                }
            }
    }

}
