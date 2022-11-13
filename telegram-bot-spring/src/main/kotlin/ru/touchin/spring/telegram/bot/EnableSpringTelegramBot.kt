package ru.touchin.spring.telegram.bot

import org.springframework.context.annotation.Import

@Import(value = [TelegramBotConfiguration::class])
annotation class EnableSpringTelegramBot
