package ru.touchin.server.info.services

import org.springframework.http.server.ServerHttpResponse

abstract class ServerInfoService {

    abstract fun addHeader(response: ServerHttpResponse): ServerHttpResponse

    fun getServerInfo() {

    }

}
