@file:Suppress("unused")
package ru.touchin.common.spring.web.webclient.errors

import org.springframework.http.HttpStatus

class WebClientStatusException(message: String, val status: HttpStatus) : Exception(message)
