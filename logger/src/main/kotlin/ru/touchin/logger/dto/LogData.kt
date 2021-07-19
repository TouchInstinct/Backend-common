package ru.touchin.logger.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import ru.touchin.logger.context.LogExecutionContextData

class LogData {

    val tags = mutableSetOf<String>()

    @JsonIgnore
    var error: Throwable? = null

    var duration: Long? = null

    val data = mutableMapOf<String, Any>()

    var method: String? = null

    var ctx: LogExecutionContextData? = null

}
