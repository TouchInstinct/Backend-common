package ru.touchin.sheets.google.utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.touchin.spreadsheets.google.utils.GoogleSpreadSheetsFormulaParserUtil
import java.net.URL

class GoogleSheetsFormulaParserUtilTest {

    private val url = URL("https://example.com")

    @Test
    fun isFormulaHyperlink_trueIfCorrectSyntax() {
        val text = "=HYPERLINK(someurl;label)"

        val result = GoogleSpreadSheetsFormulaParserUtil.isFormulaHyperlink(text, "ru_RU")

        Assertions.assertTrue(result)
    }

    @Test
    fun isFormulaHyperlink_falseIfUrl() {
        val result = GoogleSpreadSheetsFormulaParserUtil.isFormulaHyperlink(url.toString(), "ru_RU")

        Assertions.assertFalse(result)
    }

    @Test
    fun asFormulaHyperlink_correct() {
        val label = "link"

        val expected = "=HYPERLINK(\"$url\";\"$label\")"
        val actual = GoogleSpreadSheetsFormulaParserUtil.asFormulaHyperlink(url, label, "ru_RU")

        Assertions.assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun fromFormulaHyperlink_correct() {
        val expected = url.toString()
        val actual = GoogleSpreadSheetsFormulaParserUtil.fromFormulaHyperlink("=HYPERLINK(\"$url\";\"label\")", "ru_RU").toString()


        Assertions.assertEquals(
            expected,
            actual
        )
    }

}
