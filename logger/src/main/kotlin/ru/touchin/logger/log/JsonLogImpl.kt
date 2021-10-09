package ru.touchin.logger.log

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import ru.touchin.logger.dto.LogError
import ru.touchin.logger.dto.LogError.Companion.stackTraceAsString
import ru.touchin.logger.dto.LogError.Companion.toLogError

open class JsonLogImpl(clazz: Class<*>) : AbstractLog(clazz) {

    companion object {
        val objectMapper: ObjectMapper = JsonMapper.builder()
            .addModule(JavaTimeModule())
            .build()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    private fun toJson(): JsonNode {
        val result = objectMapper.convertValue(logData, ObjectNode::class.java)

        logData.error
            ?.toLogError()
            ?.let {
                objectMapper.convertValue(it, JsonNode::class.java)
            }
            ?.let {
                result.remove("error")
                result.set<ObjectNode>("error", it)
            }

        return result
    }

    override fun getMessage(): LogMessage {
        val message = runCatching {
            objectMapper.writeValueAsString(toJson())
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
