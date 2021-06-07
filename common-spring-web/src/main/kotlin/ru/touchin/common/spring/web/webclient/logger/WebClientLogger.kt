package ru.touchin.common.spring.web.webclient.logger

import ru.touchin.common.spring.web.webclient.dto.RequestLogData

interface WebClientLogger {

    fun log(requestLogData: RequestLogData)

}
