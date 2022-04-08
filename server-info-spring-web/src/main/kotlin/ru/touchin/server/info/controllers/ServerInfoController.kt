package ru.touchin.server.info.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.touchin.server.info.response.ServerInfoResponse
import ru.touchin.server.info.services.ServerInfoHeader

@RestController
@RequestMapping("/info")
class ServerInfoController(
    private val serverInfoHeaders: List<ServerInfoHeader>
) {

    @GetMapping
    fun getServerInfo(): ServerInfoResponse {
        val serverInfoList = mutableListOf<Map<String, String>>()

        for (service in serverInfoHeaders) {
            val headers = service.getHeaders()

            headers.map {
                serverInfoList.add(
                    mapOf(it)
                )
            }
        }

        return ServerInfoResponse(
            serverInfo = serverInfoList
        )
    }

}
