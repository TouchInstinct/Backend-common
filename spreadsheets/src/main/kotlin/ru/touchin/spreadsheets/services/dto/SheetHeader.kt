package ru.touchin.spreadsheets.services.dto

data class SheetHeader(
    val values: List<List<SheetCell>>
) {

    fun hasHeader() = values.isNotEmpty()

}
