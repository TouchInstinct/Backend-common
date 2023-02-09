package ru.touchin.spreadsheets.google.services

import org.springframework.beans.factory.ObjectFactory
import org.springframework.stereotype.Service
import ru.touchin.spreadsheets.services.SpreadsheetBuilder
import ru.touchin.spreadsheets.services.SpreadsheetBuilderFactory

@Service
class GoogleSpreadsheetBuilderFactoryImpl(
    private val spreadsheetBuilderFactory: ObjectFactory<SpreadsheetBuilder>,
) : SpreadsheetBuilderFactory {

    override fun create(): SpreadsheetBuilder {
        return spreadsheetBuilderFactory.`object`
    }

}
