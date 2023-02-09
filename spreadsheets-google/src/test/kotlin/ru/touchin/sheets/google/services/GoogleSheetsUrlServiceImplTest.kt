package ru.touchin.sheets.google.services

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.touchin.spreadsheets.google.exceptions.InvalidGoogleSpreadsheetsSourceException
import ru.touchin.spreadsheets.google.services.GoogleSheetsUrlServiceImpl
import ru.touchin.spreadsheets.services.SpreadsheetsUrlService
import ru.touchin.spreadsheets.services.dto.SpreadsheetSource

@SpringBootTest(
    classes = [
        GoogleSheetsUrlServiceImpl::class,
    ]
)
internal class GoogleSheetsUrlServiceImplTest {

    @Autowired
    lateinit var sheetsUrlService: SpreadsheetsUrlService

    @Test
    fun shouldParseOk() {
        val expectedSpreadsheetSource = SpreadsheetSource(
            spreadsheetId = "1KgQTBmFq-yddGotA5PUd5SRkg-J3dtdULpRNz7uctAE",
            sheetId = 1,
        )

        val url = "https://docs.google.com/spreadsheets/d/${expectedSpreadsheetSource.spreadsheetId}/edit#gid=${expectedSpreadsheetSource.sheetId}"

        val text1 = """
            Test
            Test $url 
            ssss
        """.trimIndent()

        val actualSpreadsheetSource1 = sheetsUrlService.parse(text1)

        assertEquals(expectedSpreadsheetSource, actualSpreadsheetSource1)

        val text2 = """
            Test ($url)
        """.trimIndent()

        val actualSpreadsheetSource2 = sheetsUrlService.parse(text2)

        assertEquals(expectedSpreadsheetSource, actualSpreadsheetSource2)
    }

    @Test
    fun shouldParseOk2() {
        val expectedSpreadsheetSource = SpreadsheetSource(
            spreadsheetId = "1KgQTBmFq-yddGotA5PUd5SRkg-J3dtdULpRNz7uctAE",
            sheetId = 0,
        )

        val text = """
            Test
            Test https://docs.google.com/spreadsheets/d/${expectedSpreadsheetSource.spreadsheetId}/edit Test
            Test
        """.trimIndent()

        val actualSpreadsheetSource1 = sheetsUrlService.parse(text)

        assertEquals(expectedSpreadsheetSource, actualSpreadsheetSource1)
    }

    @Test
    fun shouldThrowException() {
        val text1 = """
            Test
        """.trimIndent()

        assertThrows(InvalidGoogleSpreadsheetsSourceException::class.java) {
            sheetsUrlService.parse(text1)
        }

        val text2 = """
            Test (https://dcs.google.com/spreadsheets/d/1KgQTBmFq-yddGotA5PUd5SRkg-J3dtdULpRNz7uctAE/edit#gid=0)
        """.trimIndent()

        assertThrows(InvalidGoogleSpreadsheetsSourceException::class.java) {
            sheetsUrlService.parse(text2)
        }

        val text3 = """
            Test (https://dcs.google.com/spreadsheets/d/1KgQTBmFq-yddGotA5PUd5SRkg-J
            3dtdULpRNz7uctAE/edit#gid=0)
        """.trimIndent()

        assertThrows(InvalidGoogleSpreadsheetsSourceException::class.java) {
            sheetsUrlService.parse(text3)
        }
    }

}
