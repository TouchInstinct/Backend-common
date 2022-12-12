package ru.touchin.spring.telegram.bot.messages.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.touchin.spring.telegram.bot.messages.services.TelegramMessageCommandResolver

@ActiveProfiles("test")
@SpringBootTest
internal class TelegramMessageCommandResolverImplTest {

    @Autowired
    private lateinit var telegramMessageCommandResolver: TelegramMessageCommandResolver

    @Test
    fun shouldBeEmptyCommand() {
        telegramMessageCommandResolver.resolve("hello").also {
            assertFalse(it.hasCommand())
            assertEquals("hello", it.message)
        }

        telegramMessageCommandResolver.resolve("").also {
            assertFalse(it.hasCommand())
            assertEquals("", it.message)
        }

        telegramMessageCommandResolver.resolve(" ").also {
            assertFalse(it.hasCommand())
            assertEquals(" ", it.message)
        }

        telegramMessageCommandResolver.resolve("hello world").also {
            assertFalse(it.hasCommand())
            assertEquals("hello world", it.message)
        }
    }

    @Test
    fun shouldResolveCommand() {
        telegramMessageCommandResolver.resolve("/hello").also {
            assertTrue(it.hasCommand())
            assertEquals("/hello", it.command)
            assertEquals("", it.message)
        }

        telegramMessageCommandResolver.resolve("/hello world").also {
            assertTrue(it.hasCommand())
            assertEquals("/hello", it.command)
            assertEquals("world", it.message)
        }

        telegramMessageCommandResolver.resolve("/hello /world").also {
            assertTrue(it.hasCommand())
            assertEquals("/hello", it.command)
            assertEquals("/world", it.message)
        }

        telegramMessageCommandResolver.resolve("  /hello  /world").also {
            assertTrue(it.hasCommand())
            assertEquals("/hello", it.command)
            assertEquals(" /world", it.message)
        }
    }

}
