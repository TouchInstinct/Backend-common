package ru.touchin.spring.telegram.bot.messages.dto

data class MessageCommand(
    val command: String,
    val message: String
) {

    fun hasCommand() = command != NO_COMMAND

    companion object {
        private const val NO_COMMAND = ""

        fun createNoCommandMessage(message: String) = MessageCommand(
            command = NO_COMMAND,
            message = message
        )

    }
}
