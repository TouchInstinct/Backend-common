package ru.touchin.logger.dto

import ru.touchin.logger.context.LogExecutionContextData

class LogData {

    val tags = mutableSetOf<String>()

    var error: Throwable? = null

    var duration: Long? = null

    val data = mutableMapOf<String, Any>()

    var method: String? = null

    var ctx: LogExecutionContextData? = null

}
