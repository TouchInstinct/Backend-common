package ru.touchin.info.services

import org.springframework.http.server.ServerHttpResponse

interface ServerInfoService {

    fun addHeader(response: ServerHttpResponse): ServerHttpResponse

}
