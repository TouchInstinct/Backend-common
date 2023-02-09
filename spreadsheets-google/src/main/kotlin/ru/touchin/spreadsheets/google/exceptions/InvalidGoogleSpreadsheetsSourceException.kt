package ru.touchin.spreadsheets.google.exceptions

import ru.touchin.common.exceptions.CommonException

class InvalidGoogleSpreadsheetsSourceException(
    url: String,
) : CommonException("Unable to parse source $url")
