package ru.touchin.server.info.services.version

import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import ru.touchin.server.info.services.version.properties.ServerInfoProperties
import ru.touchin.server.info.services.ServerInfoHeader

@Component
class BuildVersionHeader(
    private val serverInfoProperties: ServerInfoProperties
) : ServerInfoHeader {

    override fun getHeaders(): MultiValueMap<String, String> {
        return LinkedMultiValueMap<String, String>()
            .apply {
                this.add("X-App-Build-Version", serverInfoProperties.buildVersion)
            }
    }

}
