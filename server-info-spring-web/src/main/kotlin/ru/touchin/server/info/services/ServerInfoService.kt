package ru.touchin.server.info.services

import org.springframework.util.MultiValueMap

interface ServerInfoService {

    fun getHeaders(): MultiValueMap<String, String>

}
