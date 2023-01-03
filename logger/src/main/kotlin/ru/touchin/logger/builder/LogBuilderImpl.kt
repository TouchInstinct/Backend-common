package ru.touchin.logger.builder

import ru.touchin.logger.context.LogExecutionContextData
import ru.touchin.logger.context.LoggerExecutionContext
import ru.touchin.logger.log.Log
import ru.touchin.logger.dto.LogLevel
import ru.touchin.logger.dto.LogData
import ru.touchin.logger.dto.LogDuration
import ru.touchin.logger.dto.LogError

class LogBuilderImpl(
    val createLog: (LogData) -> Log<LogData>
) : LogBuilder<LogData> {

    private val logData: LogData = LogData()

    override fun addTags(vararg tags: String): LogBuilder<LogData> = also {
        logData.tags.addAll(tags)
    }

    override fun setError(error: Throwable?): LogBuilder<LogData> = also {
        logData.error = error

        if (error != null) {
            addTags(LogError.ERROR_TAG)
        }
    }

    override fun setDuration(duration: LogDuration): LogBuilder<LogData> = also {
        logData.duration = duration.getDurationInMs()
    }

    override fun addData(vararg items: LogDataItem): LogBuilder<LogData> = also {
        logData.data.putAll(items)
    }

    override fun setMethod(method: String): LogBuilder<LogData> = also {
        logData.method = method
    }

    override fun setContext(): LogBuilder<LogData> = also {
        logData.ctx = LoggerExecutionContext.current.get()
    }

    override fun setContext(context: LogExecutionContextData): LogBuilder<LogData> = also {
        logData.ctx = context
    }

    override fun build(): Log<LogData> {
        if (logData.ctx == null) {
            logData.ctx = LoggerExecutionContext.current.get()
        }

        return createLog(logData)
    }

    override fun isEnabled(logLevel: LogLevel): Boolean {
        return createLog(LogData()).isEnabled(logLevel)
    }

}
