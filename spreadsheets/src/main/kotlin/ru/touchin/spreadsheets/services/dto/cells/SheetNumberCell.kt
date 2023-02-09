package ru.touchin.spreadsheets.services.dto.cells

import ru.touchin.spreadsheets.services.dto.CellType
import ru.touchin.spreadsheets.services.dto.SheetCell

data class SheetNumberCell(override val value: Number) : SheetCell {

    override val type = CellType.Number

}
