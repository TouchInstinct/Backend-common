package ru.touchin.spring.telegram.bot.messages

import org.telegram.telegrambots.meta.bots.AbsSender
import ru.touchin.spring.telegram.bot.messages.dto.MessageContext

abstract class AbstractMessageHandler<C: MessageContext<U, S>, U, S>:
    MessageHandler<C, U, S>,
    MessageFilter<C, U, S>,
    HelpMessage
{

    override fun  process(context: C, sender: AbsSender): Boolean {
        if (!isSupported(context)) {
            return false
        }

        return internalProcess(context, sender)
    }

    abstract fun internalProcess(context: C, sender: AbsSender): Boolean

}
