package ru.touchin.spring.telegram.bot.messages.dto

import org.telegram.telegrambots.meta.api.objects.Update

open class MessageContext<U, S>(
    val origin: Update,
    val user: U,
    val messageCommand: MessageCommand,
    val state: S
) {

    val chatId: String
        get() = origin.message?.chatId?.toString()
            ?: origin.callbackQuery.message.chatId.toString()
}
