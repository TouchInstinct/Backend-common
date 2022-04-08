package ru.touchin.server.info.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.touchin.server.info.services.ServerInfoService

@RestController
@RequestMapping("/info")
class ServerInfoController {

    @Autowired
    private lateinit var serverInfoServices: List<ServerInfoService> //TODO(Move to constructor)

    @GetMapping
    fun getServerInfo(): List<Map<String, String>> {
        val serverInfoList = mutableListOf<Map<String, String>>() //TODO(Maybe return just map)

        for (service in serverInfoServices) {
            serverInfoList.add(service.getServerInfo())
        }

        return serverInfoList
    }

}
