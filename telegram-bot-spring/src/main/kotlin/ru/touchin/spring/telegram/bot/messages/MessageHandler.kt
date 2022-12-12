package ru.touchin.spring.telegram.bot.messages

import org.telegram.telegrambots.meta.bots.AbsSender
import ru.touchin.spring.telegram.bot.messages.dto.MessageContext

interface MessageHandler<C: MessageContext<U, S>, U, S> {

    fun process(context: C, sender: AbsSender): Boolean

}
