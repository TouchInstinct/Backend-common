package ru.touchin.spreadsheets.services.dto.cells

import ru.touchin.spreadsheets.services.dto.CellType
import ru.touchin.spreadsheets.services.dto.SheetCell

class SheetNullCell() : SheetCell {

    override val value = 0

    override val type = CellType.Null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SheetNullCell

        if (value != other.value) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value
        result = 31 * result + type.hashCode()
        return result
    }

}
