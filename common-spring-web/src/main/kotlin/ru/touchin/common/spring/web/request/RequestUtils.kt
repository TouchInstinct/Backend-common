@file:Suppress("unused")
package ru.touchin.common.spring.web.request

import javax.servlet.http.HttpServletRequest

object RequestUtils {

    fun HttpServletRequest.doesPrefixMatch(prefixes: List<String>): Boolean {
        return prefixes.any { pathPrefix ->
            this.requestURI.startsWith(pathPrefix, ignoreCase = true)
        }
    }

    val HttpServletRequest.publicIp: String get() {
        return getHeader("X-Real-Ip")
            ?: this.remoteAddr
    }

}

