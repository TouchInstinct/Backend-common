package ru.touchin.logger.log

import ru.touchin.logger.dto.LogLevel

interface Log<T> {

    var logData: T

    fun trace()
    fun info()
    fun error()

    /***
     * Use loglevel error is logData has error and use info level otherwise
     */
    fun log()

    fun isEnabled(level: LogLevel): Boolean

}
