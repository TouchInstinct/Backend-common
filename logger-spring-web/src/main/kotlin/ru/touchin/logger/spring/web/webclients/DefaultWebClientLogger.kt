package ru.touchin.logger.spring.web.webclients

import org.springframework.stereotype.Component
import ru.touchin.common.spring.web.webclient.dto.RequestLogData
import ru.touchin.common.spring.web.webclient.logger.WebClientLogger
import ru.touchin.logger.dto.LogData
import ru.touchin.logger.factory.LogBuilderFactory

@Component
class DefaultWebClientLogger(
    private val logBuilderFactory: LogBuilderFactory<LogData>
) : WebClientLogger {

    override fun log(requestLogData: RequestLogData) {
        logBuilderFactory.create(this::class.java)
            .addTags(*requestLogData.logTags.toTypedArray())
            .setMethod(requestLogData.method.toString())
            .addData("uri" to requestLogData.uri)
            .also { builder ->
                requestLogData.requestBody?.also {
                    builder.addData(
                        "requestBody" to it,
                    )
                }

                requestLogData.responseBody?.also {
                    builder.addData(
                        "responseData" to it,
                    )
                }
            }
            .build()
            .log()
    }

}
