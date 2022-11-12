package ru.touchin.server.info.services.version

import org.springframework.stereotype.Component
import ru.touchin.server.info.services.ServerInfoHeader
import ru.touchin.server.info.services.version.properties.ServerInfoProperties

@Component
class BuildVersionHeader(
    private val serverInfoProperties: ServerInfoProperties
) : ServerInfoHeader {

    override fun getHeaders(): Map<String, String> {
        return mapOf(
            "X-App-Build-Version" to serverInfoProperties.buildVersion
        )
    }

}
