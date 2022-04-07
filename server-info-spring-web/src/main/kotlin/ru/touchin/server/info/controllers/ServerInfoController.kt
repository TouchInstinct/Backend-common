package ru.touchin.server.info.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/info")
class ServerInfoController {

    @GetMapping
    fun getServerInfo() {

    }

}
