package ru.touchin.server.info.services.version

import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import ru.touchin.server.info.services.version.properties.ServerInfoProperties
import ru.touchin.server.info.services.ServerInfoService

@Service
class BuildVersionServiceImpl(
    private val serverInfoProperties: ServerInfoProperties
) : ServerInfoService {

    override fun getHeaders(): MultiValueMap<String, String> {
        return LinkedMultiValueMap<String, String>()
            .apply {
                this.add("X-App-Build-Version", serverInfoProperties.buildVersion)
            }
    }

}
