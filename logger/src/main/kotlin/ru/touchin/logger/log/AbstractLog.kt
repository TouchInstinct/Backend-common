package ru.touchin.logger.log

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
            LogLevel.Info -> logger.isInfoEnabled
            LogLevel.Error -> logger.isErrorEnabled
        }
    }

}
