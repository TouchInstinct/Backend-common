## Библиотека написания телеграмм-ботов

```kotlin
@Component
class MyTelegramBot(
    private val messageHandlers: List<MessageHandler>,
) : TelegramLongPollingBot() {

    override fun onUpdateReceived(update: Update?) {
        // create MessageContext
        // messageHandlers.takeWhile { it.process(ctx, this) }
    }

}

@Component
class HelloMessageHandler: AbstractMessageHandler {

    override fun isSupported(ctx: MessageContext): Booleat {
        ctx.messageCommand.message.equals("hi")
    }

    override fun process(ctx: MessageContext, sender: AbsSender): Boolean {
        val message = SendMessage().apply {
            this.chatId = ctx.chatId
            this.text = "Hello"
        }

        sender.execute(message)

        return true
    }

}
```
