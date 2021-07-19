@file:Suppress("unused")
package ru.touchin.logger.spring.web.interceptors

import org.springframework.http.HttpStatus
import org.springframework.web.servlet.HandlerInterceptor
import ru.touchin.common.spring.web.dto.ApiError
import ru.touchin.common.spring.web.request.RequestUtils.publicIp
import ru.touchin.logger.context.DefaultContextFields
import ru.touchin.logger.context.LoggerExecutionContext
import ru.touchin.logger.dto.LogData
import ru.touchin.logger.dto.LogDuration
import ru.touchin.logger.factory.LogBuilderFactory
import java.net.URI
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoggingInterceptor(
    private val logBuilderFactory: LogBuilderFactory<LogData>,
) : HandlerInterceptor {

    private val logDuration = LogDuration()

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        if (request.requestURI.let(::URI).path == ERROR_PATH) {
            return true
        }

        val uri = request.requestURI.let(::URI)

        LoggerExecutionContext.current.updateContext { context ->
            context.plus(
                mapOf(
                    DefaultContextFields.id.name to UUID.randomUUID().toString(),
                    DefaultContextFields.host.name to uri.host,
                    DefaultContextFields.path.name to uri.path,
                    DefaultContextFields.httpMethod.name to request.method,
                    DefaultContextFields.ipAddress.name to request.publicIp,
                )
            )
        }

        logBuilderFactory.create(this::class.java)
            .addTags(API_TAG, REQUEST_TAG)
            .build()
            .log()

        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        if (request.requestURI.let(::URI).path == ERROR_PATH) {
            return
        }

        LoggerExecutionContext.current.updateContext { data ->
            val appCode = data[DefaultContextFields.appCode.name] ?: getAppCode(response)

            appCode
                ?.let { data.plus(DefaultContextFields.appCode.name to it) }
                ?: data
        }

        logBuilderFactory.create(this::class.java)
            .setDuration(logDuration)
            .addData("httpStatusCode" to response.status)
            .addTags(API_TAG, RESPONSE_TAG)
            .build()
            .log()

    }

    private fun getAppCode(response: HttpServletResponse) = when (response.status) {
        HttpStatus.OK.value() -> ApiError.SUCCESS_CODE
        HttpStatus.NOT_FOUND.value() -> null
        HttpStatus.BAD_REQUEST.value() -> null
        else -> ApiError.FAILURE_CODE
    }

    companion object {
        private const val ERROR_PATH = "/error"

        private const val API_TAG = "api"
        private const val REQUEST_TAG = "request"
        private const val RESPONSE_TAG = "response"
    }

}
