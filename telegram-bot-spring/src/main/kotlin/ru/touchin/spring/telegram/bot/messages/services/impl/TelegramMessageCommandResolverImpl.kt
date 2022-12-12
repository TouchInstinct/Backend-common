package ru.touchin.spring.telegram.bot.messages.services.impl

import org.springframework.stereotype.Service
import ru.touchin.spring.telegram.bot.messages.dto.MessageCommand
import ru.touchin.spring.telegram.bot.messages.services.TelegramMessageCommandResolver

@Service
class TelegramMessageCommandResolverImpl : TelegramMessageCommandResolver {

    override fun resolve(rawMessage: String): MessageCommand {
        val message = rawMessage.trim()

        if (message.isBlank() || !message.startsWith("/")) {
            return MessageCommand.createNoCommandMessage(rawMessage)
        }

        return MessageCommand(
            command = message.substringBefore(' ').trim().lowercase(),
            message = message.substringAfter(' ', missingDelimiterValue = "")
        )
    }

}
