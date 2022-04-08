package ru.touchin.server.info.controllers

import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.touchin.server.info.services.ServerInfoHeader

@RestController
@RequestMapping("/info")
class ServerInfoController(
    private val serverInfoHeaders: List<ServerInfoHeader>
) {

    @GetMapping
    fun getServerInfo(): MultiValueMap<String, String> {
        val serverInfoList = LinkedMultiValueMap<String, String>()

        for (service in serverInfoHeaders) {
            serverInfoList.addAll(service.getHeaders())
        }

        return serverInfoList
    }

}
