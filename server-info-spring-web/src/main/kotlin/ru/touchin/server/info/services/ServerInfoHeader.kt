package ru.touchin.server.info.services

import org.springframework.util.MultiValueMap

interface ServerInfoHeader {

    fun getHeaders(): List<Pair<String, String>>

}
