package ru.touchin.logger.log

class SimpleLogImpl(clazz: Class<*>): AbstractLog(clazz) {

    override fun getMessage(): LogMessage {
        val builder = StringBuilder()
        val pretty = objectMapper().writerWithDefaultPrettyPrinter()

        builder.append("\n\ttags: ${logData.tags.joinToString(",")}")

        if (logData.duration != null) {
            builder.append("\n\tduration: ${logData.duration}ms")
        }

        if (logData.method != null) {
            builder.append("\n\tmethod: ${logData.method}")
        }

        if (!logData.ctx.isNullOrEmpty()) {
            builder.append("\n\tcontext:\n${pretty.writeValueAsString(logData.ctx)}")
        }

        if (logData.data.isNotEmpty()) {
            builder.append("\n\tdata:\n${pretty.writeValueAsString(logData.data)}")
        }

        return LogMessage(
            message = builder.toString(),
            error = logData.error
        )
    }

}
