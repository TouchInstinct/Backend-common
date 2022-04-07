package ru.touchin.server.info.services

import org.springframework.http.server.ServerHttpResponse
import org.springframework.stereotype.Service
import ru.touchin.server.info.properties.ServerInfoProperties

@Service
class ServerInfoServiceImpl(
    private val serverInfoProperties: ServerInfoProperties
) : ServerInfoService() {

    override fun addHeader(response: ServerHttpResponse): ServerHttpResponse {
        response
            .headers
            .add("X-App-Build-Version", serverInfoProperties.buildVersion)

        return response
    }

}
