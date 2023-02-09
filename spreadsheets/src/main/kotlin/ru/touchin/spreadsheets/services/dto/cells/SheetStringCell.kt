package ru.touchin.spreadsheets.services.dto.cells

import ru.touchin.spreadsheets.services.dto.CellType
import ru.touchin.spreadsheets.services.dto.SheetCell

data class SheetStringCell(override val value: String) : SheetCell {

    override val type = CellType.String

}
