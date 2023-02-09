package ru.touchin.spreadsheets.services.dto

data class Spreadsheet(
    val rowCount: Int,
    val columnCount: Int,
    val locale: String,
)
