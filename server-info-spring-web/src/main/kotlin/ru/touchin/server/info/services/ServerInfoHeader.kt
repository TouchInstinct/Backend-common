package ru.touchin.server.info.services

import org.springframework.util.MultiValueMap

interface ServerInfoHeader {

    fun getHeaders(): Map<String, String>

}
