package ru.touchin.info.services

import org.springframework.http.server.ServerHttpResponse
import ru.touchin.info.properties.ServerInfoProperties

class ServerInfoServiceImpl(
    private val serverInfoProperties: ServerInfoProperties
) : ServerInfoService {

    override fun addHeader(response: ServerHttpResponse): ServerHttpResponse {
        response
            .headers
            .add("X-App-Build-Version", serverInfoProperties.buildNumber)

        return response
    }

}
