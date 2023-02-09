package ru.touchin.spreadsheets.services.dto

data class SheetRange(
    val startColumnIndex: Int,
    val startRowIndex: Int,
    val endColumnIndex: Int? = null,
    val endRowIndex: Int? = null,
)
