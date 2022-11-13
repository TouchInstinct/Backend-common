package ru.touchin.spring.telegram.bot.messages.services

import ru.touchin.spring.telegram.bot.messages.dto.MessageCommand

interface TelegramMessageCommandResolver {

    fun resolve(rawMessage: String): MessageCommand

}
