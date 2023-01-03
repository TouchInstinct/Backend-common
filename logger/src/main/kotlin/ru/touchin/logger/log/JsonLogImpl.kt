package ru.touchin.logger.log

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import ru.touchin.logger.dto.LogError
import ru.touchin.logger.dto.LogError.Companion.stackTraceAsString
import ru.touchin.logger.dto.LogError.Companion.toLogError

open class JsonLogImpl(clazz: Class<*>) : AbstractLog(clazz) {

    private fun toJson(): JsonNode {
        val result = objectMapper().convertValue(logData, ObjectNode::class.java)

        logData.error
            ?.toLogError()
            ?.let {
                objectMapper().convertValue(it, JsonNode::class.java)
            }
            ?.let {
                result.remove("error")
                result.set<ObjectNode>("error", it)
            }

        return result
    }

    override fun getMessage(): LogMessage {
        val message = runCatching {
            objectMapper().writeValueAsString(toJson())
        }.getOrElse { throwable ->
            """
            {
             "tags": ["logParse", "${LogError.ERROR_TAG}", "${LogError.ERROR_FATAL_TAG}"],
             "error": {
               "message": "${throwable.message}",
               "trace": "${throwable.stackTraceAsString()}"
             }
            }
            """.trimIndent()
        }

        return LogMessage(message)
    }

}
