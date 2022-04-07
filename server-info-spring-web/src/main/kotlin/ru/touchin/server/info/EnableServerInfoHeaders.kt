package ru.touchin.server.info

import org.springframework.context.annotation.Import
import ru.touchin.server.info.configurations.ServerInfo

@Import(value = [ServerInfo::class])
annotation class EnableServerInfoHeaders
