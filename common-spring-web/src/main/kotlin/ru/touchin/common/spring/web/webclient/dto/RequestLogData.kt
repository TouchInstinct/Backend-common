package ru.touchin.common.spring.web.webclient.dto

import org.springframework.http.HttpMethod

data class RequestLogData(
    val uri: String,
    val logTags: List<String>,
    val method: HttpMethod,
    val requestBody: Any? = null,
    val responseBody: Any? = null,
)
