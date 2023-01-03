package ru.touchin.logger.builder

import ru.touchin.logger.context.LogExecutionContextData
import ru.touchin.logger.dto.LogData
import ru.touchin.logger.dto.LogDuration
import ru.touchin.logger.dto.LogLevel
import ru.touchin.logger.log.Log

typealias LogDataItem = Pair<String, Any>

interface LogBuilder<T> {

    fun addTags(vararg tags: String): LogBuilder<T>
    fun setError(error: Throwable?): LogBuilder<T>
    fun setDuration(duration: LogDuration): LogBuilder<T>
    fun addData(vararg items: LogDataItem): LogBuilder<T>
    fun setMethod(method: String): LogBuilder<T>
    fun setContext(context: LogExecutionContextData): LogBuilder<LogData>
    fun setContext(): LogBuilder<LogData>
    fun build(): Log<T>
    fun isEnabled(logLevel: LogLevel): Boolean

}
