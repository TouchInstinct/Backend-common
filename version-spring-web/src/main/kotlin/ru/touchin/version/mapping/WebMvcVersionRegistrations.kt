package ru.touchin.version.mapping

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

class WebMvcVersionRegistrations : WebMvcRegistrations {

    override fun getRequestMappingHandlerMapping(): RequestMappingHandlerMapping {
        return VersionedRequestMappingHandlerMapping()
    }

}
