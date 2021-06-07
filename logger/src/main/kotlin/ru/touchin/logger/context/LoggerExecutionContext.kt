@file:Suppress("unused")
package ru.touchin.logger.context

import ru.touchin.common.context.ExecutionContext

typealias LogExecutionContextData = Map<String, Any>

object LoggerExecutionContext {

    val current = ExecutionContext<LogExecutionContextData>(
        init = { emptyMap() }
    )

}
