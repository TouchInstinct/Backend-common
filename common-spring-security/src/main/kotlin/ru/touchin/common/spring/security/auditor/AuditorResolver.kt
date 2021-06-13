package ru.touchin.common.spring.security.auditor

interface AuditorResolver {

    fun resolve(principal: Any?): String?

}
