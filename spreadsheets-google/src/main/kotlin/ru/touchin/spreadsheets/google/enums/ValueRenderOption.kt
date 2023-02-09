package ru.touchin.spreadsheets.google.enums

/**
 * @see <a href="https://developers.google.com/sheets/api/reference/rest/v4/ValueRenderOption">Documentation</a>
 */
enum class ValueRenderOption(val code: String) {

    FORMATTED_VALUE("FORMATTED_VALUE"),
    UNFORMATTED_VALUE("UNFORMATTED_VALUE"),
    FORMULA("FORMULA"),

}
