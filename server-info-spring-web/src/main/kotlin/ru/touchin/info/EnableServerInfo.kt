package ru.touchin.info

import org.springframework.context.annotation.Import
import ru.touchin.info.configurations.ServerInfo

@Import(value = [ServerInfo::class])
annotation class EnableServerInfo
