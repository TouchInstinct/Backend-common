package ru.touchin.logger.log

class SimpleLogImpl(clazz: Class<*>): AbstractLog(clazz) {

    override fun getMessage(): LogMessage {
        val builder = StringBuilder()

        builder.append("\n\ttags: ${logData.tags.joinToString(",")}")

        if (logData.duration != null) {
            builder.append("\n\tduration: ${logData.duration}ms")
        }

        return LogMessage(
            message = builder.toString(),
            error = logData.error
        )
    }

}
