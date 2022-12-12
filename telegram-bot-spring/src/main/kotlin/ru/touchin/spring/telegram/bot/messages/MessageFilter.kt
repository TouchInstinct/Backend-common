package ru.touchin.spring.telegram.bot.messages

import ru.touchin.spring.telegram.bot.messages.dto.MessageContext

interface MessageFilter<C: MessageContext<U, S>, U, S> {

    fun isSupported(context: C): Boolean

}
