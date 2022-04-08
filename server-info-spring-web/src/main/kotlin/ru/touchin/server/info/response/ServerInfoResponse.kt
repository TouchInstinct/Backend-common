package ru.touchin.server.info.response

import org.springframework.util.MultiValueMap

data class ServerInfoResponse(
    val serverInfo: List<Map<String, String>>
)
