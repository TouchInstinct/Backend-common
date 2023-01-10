package ru.touchin.logger.log

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.slf4j.LoggerFactory
import ru.touchin.logger.dto.LogLevel
import ru.touchin.logger.dto.LogData

abstract class AbstractLog(clazz: Class<*>) : Log<LogData> {

    class LogMessage (
        val message: String,
        val error: Throwable? = null
    )

    private val logger = LoggerFactory.getLogger(clazz)
        ?: throw IllegalStateException("unable to retrieve Logger")

    override var logData: LogData = LogData()

    protected abstract fun getMessage(): LogMessage

    override fun trace() {
        if (logger.isTraceEnabled) {
            val logMessage = getMessage()

            logger.trace(logMessage.message, logMessage.error)
        }
    }

    override fun debug() {
        if (logger.isDebugEnabled) {
            val logMessage = getMessage()

            logger.debug(logMessage.message, logMessage.error)
        }
    }

    override fun info() {
        if (logger.isInfoEnabled) {
            val logMessage = getMessage()

            logger.info(logMessage.message, logMessage.error)
        }
    }

    override fun error() {
        if (logger.isErrorEnabled) {
            val logMessage = getMessage()

            logger.error(logMessage.message, logMessage.error)
        }
    }

    override fun log() {
        if (logData.error == null) {
            info()
        } else {
            error()
        }
    }

    override fun isEnabled(level: LogLevel): Boolean {
        return when(level) {
            LogLevel.Trace -> logger.isTraceEnabled
            LogLevel.Debug -> logger.isDebugEnabled
            LogLevel.Info -> logger.isInfoEnabled
            LogLevel.Error -> logger.isErrorEnabled
        }
    }

    fun objectMapper() = objectMapper

    companion object {
        val objectMapper: ObjectMapper = JsonMapper.builder()
            .addModule(JavaTimeModule())
            .build()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

}
